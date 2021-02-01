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
import uk.gov.homeoffice.drt.CiriumAppConfig
import uk.gov.homeoffice.drt.model.ArrivalTableDataIndex
import uk.gov.homeoffice.drt.repository.CiriumScheduledRepositoryI
import uk.gov.homeoffice.drt.utils.DateUtil

import scala.util.control.NoStackTrace

case class CiriumScheduledFlights(
                                   carrierFsCode: String,
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

class CiriumService[F[_] : Sync](ciriumScheduledRepository: CiriumScheduledRepositoryI[F], ciriumAppConfig: CiriumAppConfig, clientResource: Resource[F, Client[F]]) extends Http4sClientDsl[F] {

  private val logger = LoggerFactory.getLogger(getClass.getName)


  implicit lazy val ciriumScheduledFlightsDecoder: Decoder[CiriumScheduledFlights] = deriveDecoder[CiriumScheduledFlights]
  implicit lazy val ciriumScheduledResponseDecoder: Decoder[CiriumScheduledResponse] = deriveDecoder[CiriumScheduledResponse]
  implicit lazy val CiriumScheduledFlightRequestDecoder: Decoder[CiriumScheduledFlightRequest] = deriveDecoder[CiriumScheduledFlightRequest]
  implicit lazy val CiriumScheduledFlightRequestEncoder: Encoder[CiriumScheduledFlightRequest] = deriveEncoder[CiriumScheduledFlightRequest]


  def process(arrivalTableDataIndex: ArrivalTableDataIndex): F[CiriumScheduledResponse] = {
    clientResource.use { client =>
      Uri.fromString(s"${ciriumAppConfig.endPoint}/${ciriumAppConfig.api}").liftTo[F].flatMap { uri =>
        GET(makeRequestJson(arrivalTableDataIndex), uri).flatMap { req =>
          client.run(req).use { r =>
            if (r.status == Status.Ok || r.status == Status.Conflict) {
              val res = r.asJsonDecode[CiriumScheduledResponse]
              logger.info(s"Response from cirium application $res")
              res
            } else
              CiriumScheduledResponseError(
                Option(r.status.reason).getOrElse("unknown")
              ).raiseError[F, CiriumScheduledResponse]
          }
        }
      }
    }
  }


  def appendSheduledDeparture(arrivalDataIndexes: F[List[ArrivalTableDataIndex]]) = {
    arrivalDataIndexes.map(_.map { arrivalDataIndex =>
      process(arrivalDataIndex).map { sd =>
        val arrivalsTableData = arrivalDataIndex.arrivalsTableData
        sd.scheduledFlights.headOption match {
          case Some(a) => ArrivalTableDataIndex(arrivalsTableData.copy(scheduled_departure = Option(DateUtil.`yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`(a.departureTime))), arrivalDataIndex.index)
          case None => arrivalDataIndex
        }
      }
    })

  }

  def makeRequestJson(arrivalTableDataIndex: ArrivalTableDataIndex) = {
    val json = (CiriumScheduledFlightRequest(arrivalTableDataIndex.arrivalsTableData.code,
      arrivalTableDataIndex.arrivalsTableData.number,
      arrivalTableDataIndex.arrivalsTableData.scheduled.getYear,
      arrivalTableDataIndex.arrivalsTableData.scheduled.getMonth.getValue,
      arrivalTableDataIndex.arrivalsTableData.scheduled.getDayOfMonth)).asJson
    logger.info(s"Cirium request json $json")
    json
  }

}
