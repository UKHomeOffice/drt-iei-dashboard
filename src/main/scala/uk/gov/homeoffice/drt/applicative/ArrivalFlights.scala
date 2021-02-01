package uk.gov.homeoffice.drt.applicative

import cats.kernel.Semigroup
import cats.syntax.all._
import cats.{Applicative, Monad}
import uk.gov.homeoffice.drt.model.{ArrivalTableDataIndex, Arrivals, FlightsRequest}
import uk.gov.homeoffice.drt.service.{ArrivalService, CiriumService}


trait ArrivalFlights[F[_]] {
  def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Applicative](arrivalsService: ArrivalService[F], ciriumService: CiriumService[F]): ArrivalFlights[F] = new ArrivalFlights[F] {
    def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals] = {
      val arrivalFlights: F[List[ArrivalTableDataIndex]] = arrivalsService.getFlightsDetail(n)
      val arrivalFlightsWithScheduledDeparture = arrivalFlights.map(_.filter(_.arrivalsTableData.scheduled_departure.isDefined))
      val arrivalFlightsWithoutScheduledDeparture = arrivalFlights.map(_.filterNot(_.arrivalsTableData.scheduled_departure.isDefined))
      val amendedArrivalFlightsWithoutScheduledDeparture: F[List[ArrivalTableDataIndex]] = ciriumService.appendSheduledDeparture(arrivalFlightsWithoutScheduledDeparture).map(_.sequence).flatten
      val combined = arrivalFlightsWithScheduledDeparture.map(a => amendedArrivalFlightsWithoutScheduledDeparture.map(b => Semigroup[List[ArrivalTableDataIndex]].combine(a, b))).flatten

      arrivalsService.transformArrivals(combined).map(Arrivals(_))
    }
  }

}


