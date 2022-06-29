package uk.gov.homeoffice.drt.applicative

import cats.Monad
import cats.effect.Sync
import cats.syntax.all._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import uk.gov.homeoffice.drt.model.{ArrivalTableDataIndex, Arrivals, FlightsRequest}
import uk.gov.homeoffice.drt.service.FlightScheduledService

trait ArrivalFlights[F[_]] {
  def flights(n: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals]
}

object ArrivalFlights {
  implicit def apply[F[_]](implicit ev: ArrivalFlights[F]): ArrivalFlights[F] = ev

  def impl[F[_] : Sync](arrivalsService: FlightScheduledService[F]): ArrivalFlights[F] = new ArrivalFlights[F] {
    implicit val logger = Slf4jLogger.getLogger[F]

    def flights(flightsRequest: FlightsRequest)(implicit monad: Monad[F]): F[Arrivals] = {
      val startTime = System.currentTimeMillis
      val arrivals: F[List[ArrivalTableDataIndex]] = arrivalsService.getFlightsDetail(flightsRequest)
      val transformArrival = arrivalsService.transformArrivalsFromArrivalTable(flightsRequest, arrivals).map(Arrivals(_))
      val endTime = System.currentTimeMillis
      Logger[F].warn(s"Time to get arrivals for flightsRequest $flightsRequest is ${endTime-startTime} milliseconds") >>
      transformArrival
    }
  }

}


