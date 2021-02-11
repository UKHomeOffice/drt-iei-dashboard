package uk.gov.homeoffice.drt

import cats.effect.{ConcurrentEffect, ContextShift, Resource, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{AutoSlash, CORS, Logger, Timeout}
import skunk.Session
import uk.gov.homeoffice.drt.api.{ArrivalRoutes, PublicRoutes}
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, DepartureRepository}
import uk.gov.homeoffice.drt.service.{AirlineService, CiriumService, FlightScheduledService}
import uk.gov.homeoffice.drt.utils.AirlineUtil

import scala.concurrent.ExecutionContext.global
import scala.concurrent.duration._

object IEIDashboardServer {
  def stream[F[_] : ConcurrentEffect](cfg: Config, client: Client[F], session: Resource[F, Session[F]])(implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {

    val middleware: HttpRoutes[F] => HttpRoutes[F] = {
      { http: HttpRoutes[F] =>
        AutoSlash(http)
      } andThen { http: HttpRoutes[F] =>
        CORS(http, CORS.DefaultCORSConfig)
      } andThen { http: HttpRoutes[F] =>
        Timeout(60.seconds)(http)
      }
    }

    AirlineUtil.populateAirlineData

    for {
      _ <- BlazeClientBuilder[F](global).stream

      arrivalsService = new FlightScheduledService(new ArrivalRepository(session), new DepartureRepository(session))

      airlineService = new AirlineService(client)

      arrivalFlightsAlg = ArrivalFlights.impl[F](arrivalsService)

      httpApp = middleware(
        PublicRoutes.dashboardRoutes[F]() <+>
          PublicRoutes.airlineRoutes[F](cfg.airline, airlineService) <+>
          ArrivalRoutes.arrivalFlightsRoutes[F](arrivalFlightsAlg, cfg.api.permissions)
        ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F](global)
        .bindHttp(cfg.api.port.value, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain


}
