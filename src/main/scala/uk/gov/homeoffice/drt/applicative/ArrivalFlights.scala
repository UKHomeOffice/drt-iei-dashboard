package uk.gov.homeoffice.drt.applicative

import cats.syntax.all._
import cats.{Applicative, Monad}
import uk.gov.homeoffice.drt.model.{ArrivalTableDataIndex, Arrivals, FlightsRequest}
import uk.gov.homeoffice.drt.service.FlightScheduledService

import scala.util.Try


trait ArrivalFlights[F[_]] {
  def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Applicative](arrivalsService: FlightScheduledService[F]): ArrivalFlights[F] = new ArrivalFlights[F] {
    def flights(flightsRequest: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals] = {
      val arrivals: F[List[ArrivalTableDataIndex]] = arrivalsService.getFlightsDetail(flightsRequest)
      arrivalsService.transformArrivalsFromArrivalTable(flightsRequest, arrivals).map(Arrivals(_))
    }
  }

}


