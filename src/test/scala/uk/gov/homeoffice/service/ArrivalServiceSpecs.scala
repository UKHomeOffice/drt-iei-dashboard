package uk.gov.homeoffice.service

import org.specs2.matcher.Scope
import org.specs2.mutable.Specification
import uk.gov.homeoffice.model.{Arrival, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalRepository
import uk.gov.homeoffice.utils.DateUtil

class ArrivalServiceSpecs extends Specification {

  trait Context extends Scope {
    val arrivalRepository = new ArrivalRepository
    val arrivalService = new ArrivalService(arrivalRepository)
  }

  "Arrival" should {
    "return arrival flight for the requested details for ATH" in new Context {
      val requestedDetails = RequestedFlightDetails("Athens", "Greece", "2018-12-21")

      val expectedResult = List(Arrival(
        _id = "2",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZX",
        flightNumber = "6062",
        arrivingAirport = "BRB",
        origin = "ATH",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ))

      expectedResult mustEqual arrivalService.getFlightsDetail(requestedDetails)
    }
  }

  "return arrival flight for the requested details for CLJ" in new Context {
    val requestedDetails = RequestedFlightDetails("Athens", "Bulgaria", "2018-12-21")

    val expectedResult = List(Arrival(
      _id = "8",
      scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
      carrierName = "EZD",
      flightNumber = "6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
    ))

    expectedResult mustEqual arrivalService.getFlightsDetail(requestedDetails)
  }

}
