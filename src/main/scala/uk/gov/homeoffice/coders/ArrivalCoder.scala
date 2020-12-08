package uk.gov.homeoffice.coders

import cats.Applicative
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf
import uk.gov.homeoffice.model.{Arrival, Arrivals}
import uk.gov.homeoffice.utils.DateUtil

object ArrivalCoder {

  implicit val arrivalEncode: Encoder[Arrival] = new Encoder[Arrival] {
    final def apply(a: Arrival): Json = Json.obj(
      ("id",Json.fromString(a._id)),
      ("origin", Json.fromString(a.origin)),
      ("arrivalAirport", Json.fromString(a.arrivingAirport)),
      ("flightNumber", Json.fromString(a.flightNumber)),
      ("carrierName", Json.fromString(a.carrierName)),
      ("scheduledArrivalDate", Json.fromString(DateUtil.formatDate(a.scheduledArrivalDate))),
      ("scheduledDepartureTime", Json.fromString(a.scheduledDepartureTime.map(DateUtil.formatDate(_)).getOrElse("")))
    )
  }

  implicit val arrivalsEncoder: Encoder[Arrivals] = deriveEncoder[Arrivals]

  implicit def arrivalsEntityEncoder[F[_] : Applicative]: EntityEncoder[F, Arrivals] =
    jsonEncoderOf[F, Arrivals]

}
