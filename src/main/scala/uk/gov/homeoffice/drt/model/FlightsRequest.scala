package uk.gov.homeoffice.drt.model

final case class FlightsRequest(region: String, post: String, country: String, portList: List[String], date: String, timezone: String)

