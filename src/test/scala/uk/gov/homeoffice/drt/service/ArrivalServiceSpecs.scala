package uk.gov.homeoffice.drt.service

import cats.effect.{IO, Sync}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import uk.gov.homeoffice.drt.AppResource
import uk.gov.homeoffice.drt.model.{Arrival, FlightsRequest}
import uk.gov.homeoffice.drt.repository.ArrivalRepositoryStub
import uk.gov.homeoffice.drt.utils.DateUtil

class ArrivalServiceSpecs extends AsyncFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  def context = {
    val arrivalService = Sync[IO].delay(new ArrivalService[IO](new ArrivalRepositoryStub)).unsafeRunSync()
    AppResource.populateAirlineData
    arrivalService
  }


  "Arrival" should "return arrival flights for the requested details for ATH" in {

    val arrivalService: ArrivalService[IO] = context

    val requestedDetails = FlightsRequest("Athens", "Greece", "2018-12-21")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-12-21 21:35:0"),
      carrierName = "Eaglexpress Air",
      flightNumber = "EZX6062",
      arrivingAirport = "BRB",
      origin = "ATH",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 19:35:00"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult

  }


  "Arrival" should "return arrival flights for the requested details for CLJ" in {
    val arrivalService: ArrivalService[IO] = context

    val requestedDetails = FlightsRequest("Athens", "Bulgaria", "2018-12-21")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-12-21 21:35:0"),
      carrierName = "British Airways",
      flightNumber = "BA6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      scheduledDepartureTime = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 19:35:00"))
    ))


    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult
  }

}
