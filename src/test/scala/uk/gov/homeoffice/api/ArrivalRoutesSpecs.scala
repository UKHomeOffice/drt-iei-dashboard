package uk.gov.homeoffice.api

import cats.effect.IO
import org.http4s.implicits._
import org.http4s.{Method, Request, Response, Status}
import uk.gov.homeoffice.applicative.ArrivalFlights
import uk.gov.homeoffice.repository.ArrivalRepository
import uk.gov.homeoffice.service.ArrivalService

class ArrivalRoutesSpecs extends org.specs2.mutable.Specification {

  "ArrivalRoutes" >> {
    "return arrival flights for Romania" >> {
      val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens?country=Romania&date=2018-12-21"))

      arrivalFlightsResponse.status must beEqualTo(Status.Ok)
      arrivalFlightsResponse.as[String].unsafeRunSync() must beEqualTo(
        """{"arrivals":[{"origin":"CLJ","arrivalAirport":"BRS","flightNumber":"6062","carrierName":"EZY","scheduledArrivalDate":"2018-12-21 21:35:00","scheduledDepartureTime":"2018-11-23 21:35:00"}]}""")
    }

    "return empty arrival flights for default" >> {
      val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens"))
      arrivalFlightsResponse.status must beEqualTo(Status.Ok)
      arrivalFlightsResponse.as[String].unsafeRunSync() must beEqualTo(
        """{"arrivals":[]}""")
    }
  }

  private[this] def retArrivalFlights(getHW: Request[IO]): Response[IO] = {
    val arrivalFlights = ArrivalFlights.impl[IO](new ArrivalService(new ArrivalRepository))
    ArrivalRoutes.arrivalFlightsRoutes(arrivalFlights).orNotFound(getHW).unsafeRunSync()
  }

}