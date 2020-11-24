package uk.gov.homeoffice.model


import java.util.Date

case class Arrivals(arrivals: List[Arrival])

case class Arrival(scheduledArrivalDate: Date,
                   carrierName: String,
                   flightNumber: String,
                   arrivingAirport: String,
                   origin: String,
                   scheduledDepartureTime: Date)