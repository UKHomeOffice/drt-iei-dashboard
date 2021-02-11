package uk.gov.homeoffice.drt.applicative

import cats.syntax.all._
import cats.{Applicative, Monad}
import uk.gov.homeoffice.drt.model.{ArrivalTableDataIndex, Arrivals, FlightsRequest}
import uk.gov.homeoffice.drt.service.{FlightScheduledService, CiriumService}


trait ArrivalFlights[F[_]] {
  def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Applicative](arrivalsService: FlightScheduledService[F], ciriumService: CiriumService[F]): ArrivalFlights[F] = new ArrivalFlights[F] {
    def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals] = {
      val arrivalFlights: F[List[ArrivalTableDataIndex]] = arrivalsService.getFlightsDetail(n)
      arrivalsService.transformArrivals(arrivalFlights).map(Arrivals(_))
    }
  }

}


