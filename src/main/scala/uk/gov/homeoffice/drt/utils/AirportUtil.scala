package uk.gov.homeoffice.drt.utils

import uk.gov.homeoffice.drt.model.{Airport, Port}

import scala.io.Source

object AirportUtil {

  lazy val source = Source.fromResource("airports.dat")

  lazy val airportDetails: Seq[Airport] = source.getLines().map { line =>
    val a: Array[String] = line.split(",").map(_.replace("\"", "").trim)
    Airport(a(1), a(2), a(3), a(4), a(5), a(11))
  }.toList


  def getPortListForCountry(implicit country: String) = {
    airportDetails.filter(_.country == country).filterNot(a => a.iata.isEmpty && a.icao.contains("\\N")).map { a =>
      if (a.iata.isEmpty)
        Some(Port(a.icao, a.portName))
      else if (a.icao.isEmpty)
        None
      else
        Some(Port(a.iata, a.portName))
    }.toList.flatten
  }

}