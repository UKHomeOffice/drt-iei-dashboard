package uk.gov.homeoffice.drt.coders

import io.circe
import io.circe._
import io.circe.parser._
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.model.{Airline, Airlines}

class AirlineDecoderSpecs extends AsyncFlatSpec with Matchers {
  val airlinesJsonString =
    """
      |{
      |    "airlines": [
      |        {
      |            "fs": "02",
      |            "name": "Oceanic",
      |            "active": false
      |        },
      |        {
      |            "fs": "03",
      |            "name": "Bellview Airlines",
      |            "active": false
      |        },
      |        {
      |            "fs": "07",
      |            "name": "OzJet",
      |            "active": false
      |        },
      |        {
      |            "fs": "0A",
      |            "icao": "GNT",
      |            "name": "Amber Air",
      |            "active": false
      |        },
      |        {
      |            "fs": "0B",
      |            "iata": "0B",
      |            "icao": "BLA",
      |            "name": "Blue Air",
      |            "active": true
      |        }
      |    ]
      | }
    """.stripMargin

  "Decode" should "give a list Airline" in {
    val airlines: Either[circe.Error, Airlines] = AirlineDecoder.airlinesDecoder(airlinesJsonString)

    airlines mustEqual Right(
      Airlines(List(
        Airline("02", "Oceanic", None, None, false),
        Airline("03", "Bellview Airlines", None, None, false),
        Airline("07", "OzJet", None, None, false),
        Airline("0A", "Amber Air", Some("GNT"), None, false),
        Airline("0B", "Blue Air", Some("BLA"), Some("0B"), true))))

  }

  "Decode" should "give a list Airline for json" in {
    val airlines: Either[circe.Error, Airlines] = AirlineDecoder.airlineJsonDecoder(parse(airlinesJsonString).getOrElse(Json.Null))

    airlines mustEqual Right(
      Airlines(List(
        Airline("02", "Oceanic", None, None, false),
        Airline("03", "Bellview Airlines", None, None, false),
        Airline("07", "OzJet", None, None, false),
        Airline("0A", "Amber Air", Some("GNT"), None, false),
        Airline("0B", "Blue Air", Some("BLA"), Some("0B"), true))))

  }
}
