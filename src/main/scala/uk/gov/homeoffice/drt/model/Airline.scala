package uk.gov.homeoffice.drt.model

case class Airline(fs: String, name: String, icao: Option[String], iata: Option[String], active: Boolean)

case class Airlines(airlines: List[Airline])