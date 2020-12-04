package uk.gov.homeoffice.service

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.model.{Arrival, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalRepositoryStub
import uk.gov.homeoffice.utils.DateUtil

class ArrivalServiceSpecs extends AsyncFlatSpec with Matchers {

  def context = {
    val arrivalRepository = new ArrivalRepositoryStub
    val arrivalService = new ArrivalService(arrivalRepository)
    arrivalService
  }


  "Arrival" should "return arrival flight for the requested details for ATH" in {

    val arrivalService = context

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


  "Arrival" should "return arrival flight for the requested details for CLJ" in {
    val arrivalService = context

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
