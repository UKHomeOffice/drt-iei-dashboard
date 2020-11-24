package uk.gov.homeoffice.applicative

import cats.Applicative
import cats.implicits._
import uk.gov.homeoffice.model.{Arrivals, RequestedFlightDetails}
import uk.gov.homeoffice.service.ArrivalService


trait ArrivalFlights[F[_]] {
  def flights(n: RequestedFlightDetails): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Applicative](arrivalService: ArrivalService): ArrivalFlights[F] = new ArrivalFlights[F] {
    def flights(n: RequestedFlightDetails): F[Arrivals] =
      Arrivals(arrivalService.getFlightsDetail(n)).pure[F]
  }
}


