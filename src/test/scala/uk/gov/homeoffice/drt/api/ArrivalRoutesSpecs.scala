package uk.gov.homeoffice.drt.api

import cats.effect.IO
import cats.implicits.{catsSyntaxEq => _}
import org.http4s._
import org.http4s.implicits._
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryStub, DepartureRepositoryI, DepartureRepositoryStub}
import uk.gov.homeoffice.drt.service.{ArrivalService, CiriumService}
import uk.gov.homeoffice.drt.{AirlineConfig, AppResource, BaseSpec, HttpClientConfig}

import scala.concurrent.duration._

class ArrivalRoutesSpecs extends AsyncFlatSpec with BaseSpec with Matchers {

  "ArrivalRoutes" should "return arrival flights for Romania" in {
    val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens?country=Bulgaria&date=2018-12-21", headers = Headers.of(Header("X-Auth-Roles", "iei-dashboard:view"))))

    arrivalFlightsResponse.status mustEqual Status.Ok
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual
      """{"data":[{"id":"1","origin":"SOF","arrivalAirport":"BRG","flightNumber":"BA6067","carrierName":"British Airways","scheduledArrivalDate":"2018-12-21 21:35:00","scheduledDepartureTime":"2018-11-23 19:35:00"}]}"""
  }

  "ArrivalRoutes" should "return empty arrival flights for default" in {
    val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens", headers = Headers.of(Header("X-Auth-Roles", "iei-dashboard:view"))))
    arrivalFlightsResponse.status mustEqual Status.Ok
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual """{"data":[]}"""
  }


  "ArrivalRoutes" should "return Error with appropriate permission" in {
    val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens"))
    arrivalFlightsResponse.status mustEqual Status.Forbidden
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual """You need appropriate permissions to view the page."""
  }


  private[this] def retArrivalFlights(getHW: Request[IO]): Response[IO] = {
    val arrivalFlights = ArrivalFlights.impl[IO](new ArrivalService(new ArrivalRepositoryStub,new DepartureRepositoryStub), new CiriumService(AirlineConfig("http://localhost:8080", "", ""), AppResource.mkHttpClient(HttpClientConfig(2 seconds, 2 seconds)),""))
    ArrivalRoutes.arrivalFlightsRoutes(arrivalFlights, List("test-view", "iei-dashboard:view")).orNotFound(getHW).unsafeRunSync()
  }

}



