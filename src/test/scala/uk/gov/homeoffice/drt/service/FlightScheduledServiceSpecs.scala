package uk.gov.homeoffice.drt.service

import cats.effect.{IO, Sync}
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import uk.gov.homeoffice.drt.model.{Arrival, FlightsRequest}
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryStub, DepartureRepositoryStub}
import uk.gov.homeoffice.drt.utils.{AirlineUtil, DateUtil}

import java.time.LocalDateTime

class FlightScheduledServiceSpecs extends AsyncFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  def context = {
    val flightScheduledService = Sync[IO].delay(new FlightScheduledService[IO](new ArrivalRepositoryStub, new DepartureRepositoryStub[IO])).unsafeRunSync()
    AirlineUtil.populateAirlineData
    flightScheduledService
  }

  "Arrival" should "return arrival flights for the requested details for ATH" in {

    val flightScheduledService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Greece", List.empty, "2018-12-21", "UK")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-21 21:35:0", "Europe/London"),
      carrierName = "Eaglexpress Air",
      flightNumber = "EZX6062",
      arrivingAirport = "BRB",
      origin = "ATH",
      status = "Forecast",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-11-23 21:35:00", "Europe/London"))
    ))

    val arrivalTableData = flightScheduledService.getFlightsDetail(requestedDetails)
    val actualResult = flightScheduledService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult

  }


  "Arrival" should "return arrival flights for the requested details for SOF origin" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Bulgaria", List("SOF"), "2018-12-21", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-21 21:35:0", "UTC"),
      carrierName = "British Airways",
      flightNumber = "BA6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      status = "Active",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-11-23 21:35:00", "UTC"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    expectedResult mustEqual actualResult
  }

  "Arrival" should "return arrival flights for the requested details for Bulgaria" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Bulgaria", List.empty, "2018-12-21", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-21 21:35:0", "UTC"),
      carrierName = "British Airways",
      flightNumber = "BA6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      status = "Active",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-11-23 21:35:00", "UTC"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    expectedResult mustEqual actualResult
  }

  "Arrival" should "return arrival flights for the requested details for Bulgaria and status Forecast when estimatedChox is present" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Bulgaria", List("LCA"), "2018-12-21", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-21 21:35:0", "UTC"),
      carrierName = "British Airways",
      flightNumber = "BA6067",
      arrivingAirport = "BRG",
      origin = "LCA",
      status = "Scheduled",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-11-21 23:35:00", "UTC"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    expectedResult mustEqual actualResult
  }

  "Arrival" should "return arrival details and arrival table and active status when actualChox time details are present" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Moldova", List.empty, "2018-12-22", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-22 21:35:0", "UTC"),
      carrierName = "British Airways",
      flightNumber = "BA6068",
      arrivingAirport = "BRG",
      origin = "KIV",
      status = "Active",
      scheduledDepartureTime = None
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult: Seq[Arrival] = arrivalService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    expectedResult mustEqual actualResult
  }

  "Arrival" should "return arrival details and active status when actual time details are present" in {
    val arrivalService: FlightScheduledService[IO] = context

    val requestedDetails = FlightsRequest("Euromed South", "Athens", "Moldova", List.empty, "2018-12-23", "UTC")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`("2018-12-23 21:35:0", "UTC"),
      carrierName = "British Airways",
      flightNumber = "BA6069",
      arrivingAirport = "BRG",
      origin = "KIV",
      status = "Active",
      scheduledDepartureTime = None
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult: Seq[Arrival] = arrivalService.transformArrivalsFromArrivalTable(requestedDetails, arrivalTableData).unsafeRunSync()

    expectedResult mustEqual actualResult
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


  "scheduled dates" should "return date for request timezone" in {
    val flightScheduledService: FlightScheduledService[IO] = context
    val localtime = LocalDateTime.now()
    val dateTime = new DateTime(localtime.getYear, localtime.getMonthValue, localtime.getDayOfMonth, localtime.getHour, localtime.getMinute, localtime.getSecond, DateTimeZone.UTC)

    val ukDate: DateTime = flightScheduledService.localDateTimeAccordingToTimezone(FlightsRequest("", "", "", List.empty, "", "UK"), localtime)
    val utcDate: DateTime = flightScheduledService.localDateTimeAccordingToTimezone(FlightsRequest("", "", "", List.empty, "", "UTC"), localtime)
    val localToCountryNetherlandsDate: DateTime = flightScheduledService.localDateTimeAccordingToTimezone(FlightsRequest("", "", "Netherlands", List.empty, "", "Local"), localtime)
    val localToCountryItalyDate: DateTime = flightScheduledService.localDateTimeAccordingToTimezone(FlightsRequest("", "", "Italy", List.empty, "", "Local"), localtime)

    ukDate mustEqual dateTime.toDateTime(DateTimeZone.forID("Europe/London"))
    utcDate mustEqual dateTime.toDateTime(DateTimeZone.forID("UTC"))
    localToCountryNetherlandsDate mustEqual dateTime.toDateTime(DateTimeZone.forID("Europe/Amsterdam"))
    localToCountryItalyDate mustEqual dateTime.toDateTime(DateTimeZone.forID("Europe/Rome"))
  }
}
