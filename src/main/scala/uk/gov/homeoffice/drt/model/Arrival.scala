package uk.gov.homeoffice.drt.model

import org.joda.time.DateTime
import uk.gov.homeoffice.drt.repository.ArrivalTableData

case class Arrivals(data: List[Arrival])

case class Arrival(_id: String,
                   scheduledArrivalDate: DateTime,
                   carrierName: String,
                   flightNumber: String,
                   arrivingAirport: String,
                   origin: String,
                   status: String,
                   scheduledDepartureTime: Option[DateTime])

case class ArrivalTableDataIndex(arrivalsTableData: ArrivalTableData, index: Int)

