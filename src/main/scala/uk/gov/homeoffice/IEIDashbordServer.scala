package uk.gov.homeoffice

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import uk.gov.homeoffice.api.ArrivalRoutes
import uk.gov.homeoffice.applicative.ArrivalFlights
import org.http4s.implicits._
import uk.gov.homeoffice.repository.ArrivalRepository
import uk.gov.homeoffice.service.ArrivalService

import scala.concurrent.ExecutionContext.global

object IEIDashbordServer {

  def stream[F[_] : ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream

      arrivalFlightsAlg = ArrivalFlights.impl[F](new ArrivalService(new ArrivalRepository))

      httpApp = (
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
