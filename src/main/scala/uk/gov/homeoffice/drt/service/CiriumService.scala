package uk.gov.homeoffice.drt.service

import cats.effect.{Resource, Sync}
import cats.syntax.all._
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.http4s.Method.GET
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.{Status, Uri}
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.repository.ArrivalTableData
import uk.gov.homeoffice.drt.utils.DateUtil
import uk.gov.homeoffice.drt.{AirlineConfig, AppResource}

import scala.util.control.NoStackTrace

case class CiriumScheduledFlights(carrierFsCode: String,
                                  flightNumber: String,
                                  departureAirportFsCode: String,
                                  arrivalAirportFsCode: String,
                                  departureTime: String,
                                  arrivalTime: String)

case class CiriumScheduledResponse(scheduledFlights: Seq[CiriumScheduledFlights])

case class CiriumScheduledFlightRequest(flightCode: String,
                                        flightNumber: Int,
                                        year: Int,
                                        month: Int,
                                        day: Int)

case class CiriumScheduledResponseError(cause: String) extends NoStackTrace

class CiriumService[F[_] : Sync](airlineConfig: AirlineConfig, clientResource: Resource[F, Client[F]], schedulerEndpoint: String) extends Http4sClientDsl[F] {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  implicit lazy val ciriumScheduledFlightsDecoder: Decoder[CiriumScheduledFlights] = deriveDecoder[CiriumScheduledFlights]
  implicit lazy val ciriumScheduledResponseDecoder: Decoder[CiriumScheduledResponse] = deriveDecoder[CiriumScheduledResponse]
  implicit lazy val CiriumScheduledFlightRequestDecoder: Decoder[CiriumScheduledFlightRequest] = deriveDecoder[CiriumScheduledFlightRequest]
  implicit lazy val CiriumScheduledFlightRequestEncoder: Encoder[CiriumScheduledFlightRequest] = deriveEncoder[CiriumScheduledFlightRequest]


  def process(arrivalTableData: ArrivalTableData): F[CiriumScheduledResponse] = clientResource.use { client =>
    Uri.fromString(s"$schedulerEndpoint${urlParams(arrivalTableData)}?appId=${airlineConfig.appId}&appKey=${airlineConfig.appKey}").liftTo[F].flatMap { uri =>
      GET(uri).flatMap { req =>
        client.run(req).use { r =>
          if (r.status == Status.Ok || r.status == Status.Conflict) {
            logger.info(s"Response from cirium api for flight is ${r.status.reason}")
            r.asJsonDecode[CiriumScheduledResponse]
          } else {
            logger.warn(s"Response from cirium api for flight is ${r.status.reason}")
            CiriumScheduledResponseError(
              Option(r.status.reason).getOrElse("unknown")
            ).raiseError[F, CiriumScheduledResponse]
          }
        }
      }
    }
  }


  def appendScheduledDeparture(arrivalTableDatas: F[List[ArrivalTableData]]): F[List[ArrivalTableData]] = {
    val amendArrivalTableDatas = arrivalTableDatas.map(_.traverse { arrivalsTableData =>
      process(arrivalsTableData).map { sd =>
        sd.scheduledFlights.headOption match {
          case Some(a) =>
            arrivalsTableData.copy(scheduled_departure = Option(DateUtil.`yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`(a.departureTime)))
          case None => arrivalsTableData
        }
      }.handleError {
        case e: CiriumScheduledResponseError =>
          logger.warn(s"Exception while calling cirium api ${e.getCause} ${e.getMessage}")
          arrivalsTableData
        case e => logger.warn(s"Error while calling cirium api ${e.getCause} ${e.getMessage}")
          arrivalsTableData
      }
    }).flatten
    amendArrivalTableDatas.map(_.filter(_.scheduled_departure.isDefined))
  }

  def makeRequestJson(arrivalsTableData: ArrivalTableData) = CiriumScheduledFlightRequest(arrivalsTableData.code,
    arrivalsTableData.number,
    arrivalsTableData.scheduled.getYear,
    arrivalsTableData.scheduled.getMonth.getValue,
    arrivalsTableData.scheduled.getDayOfMonth).asJson


  def urlParams(arrivalsTableData: ArrivalTableData) = {
    val carrierCode = AppResource.carrierCode(arrivalsTableData.code, arrivalsTableData.number.toString)
    s"$carrierCode/" +
      s"${arrivalsTableData.number}" +
      s"/departing/" +
      s"${arrivalsTableData.scheduled.getYear}" +
      s"/${arrivalsTableData.scheduled.getMonth.getValue}/" +
      s"${arrivalsTableData.scheduled.getDayOfMonth}"
  }


}
