package uk.gov.homeoffice.drt.service

import cats.effect.{IO, Sync}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import uk.gov.homeoffice.drt.drt.model.{Arrival, RequestedFlightDetails}
import uk.gov.homeoffice.drt.drt.service.ArrivalService
import uk.gov.homeoffice.drt.drt.utils.DateUtil
import uk.gov.homeoffice.drt.repository.ArrivalRepositoryStub

class ArrivalServiceSpecs extends AsyncFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  def context = {
    val arrivalService = Sync[IO].delay(new ArrivalService[IO](new ArrivalRepositoryStub)).unsafeRunSync()
    arrivalService
  }


  "Arrival" should "return arrival flight for the requested details for ATH" in {

    val arrivalService: ArrivalService[IO] = context

    val requestedDetails = RequestedFlightDetails("Athens", "Greece", "2018-12-21")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
      carrierName = "EZX",
      flightNumber = "6062",
      arrivingAirport = "BRB",
      origin = "ATH",
      scheduledDepartureTime = Some(DateUtil.parseDate("2018-11-23 19:35:00"))
    ))

    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult

  }


  "Arrival" should "return arrival flight for the requested details for CLJ" in {
    val arrivalService: ArrivalService[IO] = context

    val requestedDetails = RequestedFlightDetails("Athens", "Bulgaria", "2018-12-21")

    val expectedResult = List(Arrival(
      _id = "1",
      scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
      carrierName = "EZD",
      flightNumber = "6067",
      arrivingAirport = "BRG",
      origin = "SOF",
      scheduledDepartureTime = Some(DateUtil.parseDate("2018-11-23 19:35:00"))
    ))


    val arrivalTableData = arrivalService.getFlightsDetail(requestedDetails)
    val actualResult = arrivalService.transformArrivals(arrivalTableData).unsafeRunSync()

    actualResult mustEqual expectedResult
  }

}
