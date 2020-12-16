package uk.gov.homeoffice.drt.model

import java.util.Date

case class Arrivals(data: List[Arrival])

case class Arrival(_id: String,
                   scheduledArrivalDate: Date,
                   carrierName: String,
                   flightNumber: String,
                   arrivingAirport: String,
                   origin: String,
                   scheduledDepartureTime: Option[Date])