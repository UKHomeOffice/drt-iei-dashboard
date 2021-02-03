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
import uk.gov.homeoffice.drt.model.ArrivalTableDataIndex
import uk.gov.homeoffice.drt.repository.CiriumScheduledRepositoryI
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

class CiriumService[F[_] : Sync](ciriumScheduledRepository: CiriumScheduledRepositoryI[F], airlineConfig: AirlineConfig, clientResource: Resource[F, Client[F]]) extends Http4sClientDsl[F] {

  private val logger = LoggerFactory.getLogger(getClass.getName)


  implicit lazy val ciriumScheduledFlightsDecoder: Decoder[CiriumScheduledFlights] = deriveDecoder[CiriumScheduledFlights]
  implicit lazy val ciriumScheduledResponseDecoder: Decoder[CiriumScheduledResponse] = deriveDecoder[CiriumScheduledResponse]
  implicit lazy val CiriumScheduledFlightRequestDecoder: Decoder[CiriumScheduledFlightRequest] = deriveDecoder[CiriumScheduledFlightRequest]
  implicit lazy val CiriumScheduledFlightRequestEncoder: Encoder[CiriumScheduledFlightRequest] = deriveEncoder[CiriumScheduledFlightRequest]


  def process(arrivalTableDataIndex: ArrivalTableDataIndex): F[CiriumScheduledResponse] = clientResource.use { client =>
    Uri.fromString(s"https://api.flightstats.com/flex/schedules/rest/v1/json/flight/${urlParams(arrivalTableDataIndex)}?appId=${airlineConfig.appId}&appKey=${airlineConfig.appKey}").liftTo[F].flatMap { uri =>
      GET(uri).flatMap { req =>
        client.run(req).use { r =>
          if (r.status == Status.Ok || r.status == Status.Conflict) {
            val res = r.asJsonDecode[CiriumScheduledResponse]
            logger.info(s"Response from cirium api for flight ${urlParams(arrivalTableDataIndex)} is ${r.status.reason}")
            res
          } else
            logger.info(s"Response from cirium api for flight ${urlParams(arrivalTableDataIndex)} is ${r.status.reason}")
          CiriumScheduledResponseError(
            Option(r.status.reason).getOrElse("unknown")
          ).raiseError[F, CiriumScheduledResponse]
        }
      }
    }
  }


  def appendScheduledDeparture(arrivalDataIndexes: F[List[ArrivalTableDataIndex]]): F[List[ArrivalTableDataIndex]] = {
    arrivalDataIndexes.map(_.traverse { arrivalDataIndex =>
      process(arrivalDataIndex).map { sd =>
        val arrivalsTableData = arrivalDataIndex.arrivalsTableData
        sd.scheduledFlights.headOption match {
          case Some(a) => ArrivalTableDataIndex(arrivalsTableData.copy(scheduled_departure = Option(DateUtil.`yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`(a.departureTime))), arrivalDataIndex.index)
          case None => arrivalDataIndex
        }
      }.handleError {
        case e: CiriumScheduledResponseError =>
          logger.warn(s"Exception while calling cirium api $e")
          arrivalDataIndex
        case e => logger.warn(s"Error while calling cirium api $e")
          arrivalDataIndex
      }
    }
    ).flatten
  }

  def makeRequestJson(arrivalTableDataIndex: ArrivalTableDataIndex) = CiriumScheduledFlightRequest(arrivalTableDataIndex.arrivalsTableData.code,
    arrivalTableDataIndex.arrivalsTableData.number,
    arrivalTableDataIndex.arrivalsTableData.scheduled.getYear,
    arrivalTableDataIndex.arrivalsTableData.scheduled.getMonth.getValue,
    arrivalTableDataIndex.arrivalsTableData.scheduled.getDayOfMonth).asJson


  def urlParams(arrivalTableDataIndex: ArrivalTableDataIndex) = {
    val carrierCode = AppResource.carrierCode(arrivalTableDataIndex.arrivalsTableData.code, arrivalTableDataIndex.arrivalsTableData.number.toString)
    s"$carrierCode/" +
      s"${arrivalTableDataIndex.arrivalsTableData.number}" +
      s"/departing/" +
      s"${arrivalTableDataIndex.arrivalsTableData.scheduled.getYear}" +
      s"/${arrivalTableDataIndex.arrivalsTableData.scheduled.getMonth.getValue}/" +
      s"${arrivalTableDataIndex.arrivalsTableData.scheduled.getDayOfMonth}"
  }


}
