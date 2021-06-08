package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.syntax.all._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.circe.syntax._
import org.http4s.Method.GET
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.{Status, Uri}
import uk.gov.homeoffice.drt.AirlineConfig
import uk.gov.homeoffice.drt.coders.CiriumCoder._
import uk.gov.homeoffice.drt.model.{CiriumScheduledFlightRequest, CiriumScheduledResponse, CiriumScheduledResponseError}
import uk.gov.homeoffice.drt.repository.ArrivalTableData
import uk.gov.homeoffice.drt.utils.DateUtil


class CiriumService[F[_] : Sync](airlineConfig: AirlineConfig, client: Client[F], schedulerEndpoint: String) extends Http4sClientDsl[F] {

  implicit val logger = Slf4jLogger.getLogger[F]

  def process(arrivalTableData: ArrivalTableData): F[CiriumScheduledResponse] =
    Uri.fromString(s"$schedulerEndpoint${urlParams(arrivalTableData)}?appId=${airlineConfig.appId}&appKey=${airlineConfig.appKey}").liftTo[F].flatMap { uri =>
      GET(uri).flatMap { req =>
        client.run(req).use { r =>
          if (r.status == Status.Ok || r.status == Status.Conflict) {
            r.asJsonDecode[CiriumScheduledResponse]
          } else {
            Logger[F].warn(s"Response from cirium api for flight is ${r.status.reason}") >>
            CiriumScheduledResponseError(
              Option(r.status.reason).getOrElse("unknown")
            ).raiseError[F, CiriumScheduledResponse]
          }
        }
      }
    }

  def appendScheduledDeparture(arrivalTableDatas: F[List[ArrivalTableData]]): F[List[ArrivalTableData]] = {
    val amendArrivalTableDatas: F[List[ArrivalTableData]] = arrivalTableDatas.map(_.traverse { arrivalsTableData =>
      process(arrivalsTableData).map { sd =>
        sd.scheduledFlights.headOption match {
          case Some(a) =>
            arrivalsTableData.copy(scheduled_departure = Option(DateUtil.`yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`(a.departureTime)))
          case None => arrivalsTableData
        }
      }.handleErrorWith {
        case e: CiriumScheduledResponseError =>
          Logger[F].warn(s"Exception while calling cirium api ${e.getCause} ${e.getMessage}").as(arrivalsTableData)
        case e => Logger[F].warn(s"Error while calling cirium api ${e.getCause} ${e.getMessage}").as(arrivalsTableData)
      }
    }).flatten
    amendArrivalTableDatas.map(_.filter(_.scheduled_departure.isDefined))
  }

  private def makeRequestJson(arrivalsTableData: ArrivalTableData) = CiriumScheduledFlightRequest(arrivalsTableData.code,
    arrivalsTableData.number,
    arrivalsTableData.scheduled.getYear,
    arrivalsTableData.scheduled.getMonth.getValue,
    arrivalsTableData.scheduled.getDayOfMonth).asJson

  private def deriveCarrierCode(code: String, number: String): String = {
    code.stripSuffix(number).stripSuffix("0").stripSuffix("0")
  }

  def urlParams(arrivalsTableData: ArrivalTableData) = {
    val carrierCode = deriveCarrierCode(arrivalsTableData.code, arrivalsTableData.number.toString)
    s"$carrierCode/" +
      s"${arrivalsTableData.number}" +
      s"/departing/" +
      s"${arrivalsTableData.scheduled.getYear}" +
      s"/${arrivalsTableData.scheduled.getMonth.getValue}/" +
      s"${arrivalsTableData.scheduled.getDayOfMonth}"
  }
}
