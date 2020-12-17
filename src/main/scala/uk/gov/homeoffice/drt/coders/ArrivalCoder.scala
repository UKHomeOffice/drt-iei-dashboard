package uk.gov.homeoffice.drt.coders

import cats.Applicative
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf
import uk.gov.homeoffice.drt.model.{Arrival, Arrivals}
import uk.gov.homeoffice.drt.utils.DateUtil

object ArrivalCoder {

  implicit val arrivalEncode: Encoder[Arrival] = new Encoder[Arrival] {
    final def apply(a: Arrival): Json = Json.obj(
      ("id", Json.fromString(a._id)),
      ("origin", Json.fromString(a.origin)),
      ("arrivalAirport", Json.fromString(a.arrivingAirport)),
      ("flightNumber", Json.fromString(a.flightNumber)),
      ("carrierName", Json.fromString(a.carrierName)),
      ("scheduledArrivalDate", Json.fromString(DateUtil.`yyyy-MM-dd HH:mm:ss_format_toString`(a.scheduledArrivalDate))),
      ("scheduledDepartureTime", Json.fromString(a.scheduledDepartureTime.map(DateUtil.`yyyy-MM-dd HH:mm:ss_format_toString`(_)).getOrElse("")))
    )
  }

  implicit val arrivalsEncoder: Encoder[Arrivals] = deriveEncoder[Arrivals]

  implicit def arrivalsEntityEncoder[F[_] : Applicative]: EntityEncoder[F, Arrivals] =
    jsonEncoderOf[F, Arrivals]

}
