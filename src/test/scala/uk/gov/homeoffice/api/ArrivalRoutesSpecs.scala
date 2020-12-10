package uk.gov.homeoffice.api

import cats.effect.IO
import cats.implicits.{catsSyntaxEq => _}
import org.http4s.{Method, Request, Response, Status}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.applicative.ArrivalFlights
import uk.gov.homeoffice.repository.ArrivalRepositoryStub
import uk.gov.homeoffice.service.ArrivalService
import org.http4s.implicits._

class ArrivalRoutesSpecs extends AsyncFlatSpec with Matchers {

  "ArrivalRoutes" should "return arrival flights for Romania" in {
    val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens?country=Bulgaria&date=2018-12-21"))

    arrivalFlightsResponse.status mustEqual Status.Ok
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual
      """{"data":[{"id":"1","origin":"SOF","arrivalAirport":"BRG","flightNumber":"6067","carrierName":"EZD","scheduledArrivalDate":"2018-12-21 21:35:00","scheduledDepartureTime":"2018-11-23 21:35:00"}]}"""
  }

  "ArrivalRoutes" should "return empty arrival flights for default" in {
    val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens"))
    arrivalFlightsResponse.status mustEqual Status.Ok
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual """{"data":[]}"""
  }


  private[this] def retArrivalFlights(getHW: Request[IO]): Response[IO] = {
    val arrivalFlights = ArrivalFlights.impl[IO](new ArrivalService(new ArrivalRepositoryStub))
    ArrivalRoutes.arrivalFlightsRoutes(arrivalFlights).orNotFound(getHW).unsafeRunSync()
  }

}



