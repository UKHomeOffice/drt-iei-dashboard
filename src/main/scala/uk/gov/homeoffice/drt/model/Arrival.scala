package uk.gov.homeoffice.drt.model

import uk.gov.homeoffice.drt.repository.ArrivalTableData

import java.util.Date

case class Arrivals(data: List[Arrival])

case class Arrival(_id: String,
                   scheduledArrivalDate: Date,
                   carrierName: String,
                   flightNumber: String,
                   arrivingAirport: String,
                   origin: String,
                   status: String,
                   scheduledDepartureTime: Option[Date])

case class ArrivalTableDataIndex(arrivalsTableData: ArrivalTableData, index: Int)

