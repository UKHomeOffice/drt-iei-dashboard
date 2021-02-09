package uk.gov.homeoffice.drt

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{AutoSlash, CORS, Logger, Timeout}
import uk.gov.homeoffice.drt.api.{ArrivalRoutes, PublicRoutes}
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, DepartureRepository}
import uk.gov.homeoffice.drt.service.{AirlineService, ArrivalService, CiriumService}

import scala.concurrent.ExecutionContext.global
import scala.concurrent.duration._

object IEIDashboardServer {
  def stream[F[_] : ConcurrentEffect](cfg: Config)(implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {

    val middleware: HttpRoutes[F] => HttpRoutes[F] = {
      { http: HttpRoutes[F] =>
        AutoSlash(http)
      } andThen { http: HttpRoutes[F] =>
        CORS(http, CORS.DefaultCORSConfig)
      } andThen { http: HttpRoutes[F] =>
        Timeout(60.seconds)(http)
      }
    }

    for {
      _ <- BlazeClientBuilder[F](global).stream

      session = AppResource.session(cfg.database)

      clientResource = AppResource.mkHttpClient(cfg.httpClient)

      _ = AppResource.populateAirlineData

      arrivalsService = new ArrivalService(new ArrivalRepository(session), new DepartureRepository(session))

      airlineService = new AirlineService(clientResource)

      ciriumService = new CiriumService(cfg.airline, clientResource, cfg.cronJob.ciriumSchedulesEndpoint)

      arrivalFlightsAlg = ArrivalFlights.impl[F](arrivalsService, ciriumService)

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
