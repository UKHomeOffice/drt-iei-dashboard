package uk.gov.homeoffice.drt.service

import cats.effect.{IO, Sync}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import uk.gov.homeoffice.drt.model.{Arrival, FlightsRequest}
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryStub, DepartureRepositoryStub}
import uk.gov.homeoffice.drt.utils.{AirlineUtil, DateUtil}

class FlightScheduledServiceSpecs extends AsyncFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  def context = {
    val flightScheduledService = Sync[IO].delay(new FlightScheduledService[IO](new ArrivalRepositoryStub, new DepartureRepositoryStub[IO])).unsafeRunSync()
    AirlineUtil.populateAirlineData
    flightScheduledService
  }

  "Arrival" should "return arrival flights for the requested details for ATH" in {

    val flightScheduledService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Greece", "2018-12-21", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-12-21 21:35:0"),
      carrierName = "Eaglexpress Air",
      flightNumber = "EZX6062",
      arrivingAirport = "BRB",
      origin = "ATH",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00"))
    ))

    val arrivalTableData = flightScheduledService.getFlightsDetail(requestedDetails)
    val actualResult = flightScheduledService.transformArrivals(requestedDetails, arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult

  }


  "Arrival" should "return arrival flights for the requested details for CLJ" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Bulgaria", "2018-12-21", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-12-21 21:35:0"),
      carrierName = "British Airways",
      flightNumber = "BA6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(requestedDetails, arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult
  }

  "Arrival" should "return arrival details without scheduled Departure date when departure time is not present in departure table and arrival table" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Moldova", "2018-12-23", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-12-23 21:35:0"),
      carrierName = "British Airways",
      flightNumber = "BA6069",
      arrivingAirport = "BRG",
      origin = "KIV",
      scheduledDepartureTime = None
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(requestedDetails, arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult
  }


  "Arrival" should "give carrierName 'Ryanair' for flightNumber 'FR0012'" in {
    val arrivalService: FlightScheduledService[IO] = context

    val a = arrivalService.carrierName("FR0012", "12")

    a mustEqual "Ryanair"
  }

  "Arrival" should "give carrierName 'Blue Air' for flightNumber '0B1531'" in {
    val arrivalService: FlightScheduledService[IO] = context
    val a = arrivalService.carrierName("0B1531", "1531")

    a mustEqual "Blue Air"

  }
}
