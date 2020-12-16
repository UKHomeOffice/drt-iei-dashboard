package uk.gov.homeoffice.drt

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import uk.gov.homeoffice.drt.api.{ArrivalRoutes, PublicRoutes}
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.repository.ArrivalRepository
import uk.gov.homeoffice.drt.service.{AirlineService, ArrivalService}

import scala.concurrent.ExecutionContext.global

object IEIDashboardServer {
  def stream[F[_] : ConcurrentEffect](cfg: Config)(implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      _ <- BlazeClientBuilder[F](global).stream

      session = ResourceObject.session(cfg.database)

      clientResource = BlazeClientBuilder[F](global).resource
      airlineService = new AirlineService(clientResource)

//      _ = ResourceObject.updateAirLines(airlineService)
      _ = ResourceObject.populateAirlineData

      arrivalsService = new ArrivalService(new ArrivalRepository(session))

      arrivalFlightsAlg = ArrivalFlights.impl[F](arrivalsService)

      httpApp = (
        PublicRoutes.dashboardRoutes[F]() <+>
          PublicRoutes.airlineRoutes[F](airlineService) <+>
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
