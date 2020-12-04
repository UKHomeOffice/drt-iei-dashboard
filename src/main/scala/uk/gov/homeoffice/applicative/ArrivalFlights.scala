package uk.gov.homeoffice.applicative

import cats.Applicative
import cats.syntax.all._
import uk.gov.homeoffice.model.{Arrivals, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalTableData
import uk.gov.homeoffice.service.ArrivalService


trait ArrivalFlights[F[_]] {
  def flights(n: RequestedFlightDetails): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Applicative](arrivalsService: ArrivalService[F]): ArrivalFlights[F] = new ArrivalFlights[F] {
    def flights(n: RequestedFlightDetails): F[Arrivals] = {
      val a: F[List[(ArrivalTableData, Int)]] = arrivalsService.getFlightsDetail(n)
      arrivalsService.transformArrivals(a).map(Arrivals(_))
    }
  }
}


