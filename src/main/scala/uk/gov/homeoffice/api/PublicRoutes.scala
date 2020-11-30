package uk.gov.homeoffice.api

import java.util.concurrent.Executors

import cats.effect._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpRoutes, StaticFile}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

object PublicRoutes {
  val logger = LoggerFactory.getLogger("uk.gov.homeoffice.api.PublicRoutes")

  val dbExecutionContext = ExecutionContext.global // replace with your DB specific EC.

  val blockingPool = Executors.newFixedThreadPool(4)
  val blocker = Blocker.liftExecutorService(blockingPool)

  def dashboardRoutes[F[_] : Sync : ContextShift](): HttpRoutes[F] = {

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

          .getOrElseF(NotFound()) // In case the file doesn't exist
      case request@GET -> Root / "static" / "js" / path =>
        StaticFile.fromResource(s"ui/static/js/$path", blocker, Some(request))
          .getOrElseF(NotFound()) // In case the file doesn't exist

      case request@GET -> Root / "images" / path =>
        StaticFile.fromResource(s"ui/images/$path", blocker, Some(request))
          .getOrElseF(NotFound()) // In case the file doesn't exist

    }
  }


}