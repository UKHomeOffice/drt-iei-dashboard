package uk.gov.homeoffice.api

import java.util.Date

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import uk.gov.homeoffice.Util
import uk.gov.homeoffice.applicative.ArrivalFlights
import uk.gov.homeoffice.coders.ArrivalCoder._
import uk.gov.homeoffice.model.RequestedFlightDetails

object ArrivalRoutes {

  //http://localhost:8080/flights/athens?country=Greece&date=2018-12-12

  def arrivalFlightsRoutes[F[_] : Sync](H: ArrivalFlights[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "flights" / region :? params =>
        val country = params.getOrElse("country", List("Greece")).head
        val date = params.getOrElse("date", List(Util.formatRequestDate(new Date()))).head
        for {
          arrivals <- H.flights(RequestedFlightDetails(region, country, date))
          resp <- Ok(arrivals)
        } yield resp
    }
  }
}


