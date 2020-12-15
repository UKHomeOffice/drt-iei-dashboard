package uk.gov.homeoffice.drt.drt

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import uk.gov.homeoffice.drt.drt.api.{ArrivalRoutes, PublicRoutes}
import uk.gov.homeoffice.drt.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.drt.repository.ArrivalRepository
import uk.gov.homeoffice.drt.drt.service.ArrivalService

import scala.concurrent.ExecutionContext.global

object IEIDashbordServer {
  def stream[F[_] : ConcurrentEffect](cfg: Config)(implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream

      session = SessionResource.session(cfg.database)

      arrivalsService = new ArrivalService(new ArrivalRepository(session))

      arrivalFlightsAlg = ArrivalFlights.impl[F](arrivalsService)

      httpApp = (
        PublicRoutes.dashboardRoutes[F]() <+>
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
