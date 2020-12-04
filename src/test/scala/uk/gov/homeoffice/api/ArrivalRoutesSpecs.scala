package uk.gov.homeoffice.api

import cats.implicits.{catsSyntaxEq => _}

//class ArrivalRoutesSpecs extends org.specs2.mutable.Specification {
//
//  "ArrivalRoutes" >> {
//    "return arrival flights for Romania" >> {
//      val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens?country=Bulgaria&date=2018-12-21"))
//
//      arrivalFlightsResponse.status must beEqualTo(Status.Ok)
//      arrivalFlightsResponse.as[String].unsafeRunSync() must beEqualTo(
//        """{"data":[{"id":"8","origin":"SOF","arrivalAirport":"BRG","flightNumber":"6067","carrierName":"EZD","scheduledArrivalDate":"2018-12-21 21:35:00","scheduledDepartureTime":"2018-11-23 21:35:00"}]}""")
//    }
//
//    "return empty arrival flights for default" >> {
//      val arrivalFlightsResponse = retArrivalFlights(Request[IO](Method.GET, uri"/flights/athens"))
//      arrivalFlightsResponse.status must beEqualTo(Status.Ok)
//      arrivalFlightsResponse.as[String].unsafeRunSync() must beEqualTo(
//        """{"data":[]}""")
//    }
//  }
//
//  private[this] def retArrivalFlights(getHW: Request[IO]): Response[IO] = {
//    val arrivalFlights = ArrivalFlights.impl[IO](new ArrivalService(new ArrivalRepository))
//    ArrivalRoutes.arrivalFlightsRoutes(arrivalFlights).orNotFound(getHW).unsafeRunSync()
//  }
//
//}



