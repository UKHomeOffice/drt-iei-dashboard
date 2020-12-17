package uk.gov.homeoffice.drt.coders

import io.circe
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.homeoffice.drt.model.Airlines


object AirlineDecoder {

  def airlinesDecoder(json: String): Either[circe.Error, Airlines] = decode[Airlines](json)

  def airlineJsonDecoder(json: Json): Either[circe.Error, Airlines] = decode[Airlines](json.toString())

}