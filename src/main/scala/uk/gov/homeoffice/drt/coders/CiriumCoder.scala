package uk.gov.homeoffice.drt.coders

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import uk.gov.homeoffice.drt.model.{CiriumScheduledFlightRequest, CiriumScheduledFlights, CiriumScheduledResponse}

object CiriumCoder {

  implicit lazy val ciriumScheduledFlightsDecoder: Decoder[CiriumScheduledFlights] = deriveDecoder[CiriumScheduledFlights]
  implicit lazy val ciriumScheduledFlightsEncoder: Encoder[CiriumScheduledFlights] = deriveEncoder[CiriumScheduledFlights]
  implicit lazy val ciriumScheduledResponseDecoder: Decoder[CiriumScheduledResponse] = deriveDecoder[CiriumScheduledResponse]
  implicit lazy val ciriumScheduledResponseEncoder: Encoder[CiriumScheduledResponse] = deriveEncoder[CiriumScheduledResponse]
  implicit lazy val CiriumScheduledFlightRequestDecoder: Decoder[CiriumScheduledFlightRequest] = deriveDecoder[CiriumScheduledFlightRequest]
  implicit lazy val CiriumScheduledFlightRequestEncoder: Encoder[CiriumScheduledFlightRequest] = deriveEncoder[CiriumScheduledFlightRequest]

}
