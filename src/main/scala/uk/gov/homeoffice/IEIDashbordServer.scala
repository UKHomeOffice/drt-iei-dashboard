package uk.gov.homeoffice

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import uk.gov.homeoffice.api.{ArrivalRoutes, PublicRoutes}
import uk.gov.homeoffice.applicative.ArrivalFlights
import uk.gov.homeoffice.repository.ArrivalsRepository
import uk.gov.homeoffice.service.ArrivalService

import scala.concurrent.ExecutionContext.global

object IEIDashbordServer {
  def stream[F[_] : ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream


      session = SessionResource.session

      arrivalsService = new ArrivalService(new ArrivalsRepository(session))

      arrivalFlightsAlg = ArrivalFlights.impl[F](arrivalsService)

      httpApp = (
        PublicRoutes.dashboardRoutes[F]() <+>
          ArrivalRoutes.arrivalFlightsRoutes[F](arrivalFlightsAlg)
        ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F](global)
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain


}
