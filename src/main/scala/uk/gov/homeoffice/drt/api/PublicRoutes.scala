package uk.gov.homeoffice.drt.api

import cats.effect._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpRoutes, StaticFile}
import java.util.concurrent.Executors

object PublicRoutes {

  val blockingPool = Executors.newFixedThreadPool(4)
  val blocker = Blocker.liftExecutorService(blockingPool)

  def dashboardRoutes[F[_] : Sync : ContextShift : Logger](): HttpRoutes[F] = {
    implicit val logger = Slf4jLogger.getLogger[F]

    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case request@GET -> Root =>
        StaticFile.fromResource(s"ui/index.html", blocker, Some(request))
          .getOrElseF(NotFound())

      case request@GET -> Root / path =>
        path match {
          case a if a == "" || a.isEmpty =>
            StaticFile.fromResource(s"ui/index.html", blocker, Some(request))
              .getOrElseF(NotFound())
          case _ =>
            StaticFile.fromResource(s"ui/$path", blocker, Some(request))
              .getOrElseF(NotFound())
        }

      case request@GET -> Root / "static" / "css" / path =>
        StaticFile.fromResource(s"ui/static/css/$path", blocker, Some(request))
          .getOrElseF(NotFound())

      case request@GET -> Root / "static" / "js" / path =>
        StaticFile.fromResource(s"ui/static/js/$path", blocker, Some(request))
          .getOrElseF(NotFound())

      case request@GET -> Root / "images" / path =>
        StaticFile.fromResource(s"ui/images/$path", blocker, Some(request))
          .getOrElseF(NotFound())

    }
  }

}