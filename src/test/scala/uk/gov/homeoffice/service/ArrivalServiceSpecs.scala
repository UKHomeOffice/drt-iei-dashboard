package uk.gov.homeoffice.service

import org.specs2.matcher.Scope
import org.specs2.mutable.Specification
import uk.gov.homeoffice.Util
import uk.gov.homeoffice.model.{Arrival, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalRepository

class ArrivalServiceSpecs extends Specification {

  trait Context extends Scope {
    val arrivalRepository = new ArrivalRepository
    val arrivalService = new ArrivalService(arrivalRepository)
  }

  "Arrival" should {
    "return arrival flight for the requested details for ATH" in new Context {
      val requestedDetails = RequestedFlightDetails("Athens", "Greece", "2018-12-21")

      val expectedResult = List(Arrival(
        scheduledArrivalDate = Util.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZY",
        flightNumber = "6062",
        arrivingAirport = "BRS",
        origin = "ATH",
        scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
      ))

      expectedResult mustEqual arrivalService.getFlightsDetail(requestedDetails)
    }
  }

  "return arrival flight for the requested details for CLJ" in new Context {
    val requestedDetails = RequestedFlightDetails("Athens", "Romania", "2018-12-21")

    val expectedResult = List(Arrival(
      scheduledArrivalDate = Util.parseDate("2018-12-21 21:35:0"),
      carrierName = "EZY",
      flightNumber = "6062",
      arrivingAirport = "BRS",
      origin = "CLJ",
      scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
    ))

    expectedResult mustEqual arrivalService.getFlightsDetail(requestedDetails)
  }

}
