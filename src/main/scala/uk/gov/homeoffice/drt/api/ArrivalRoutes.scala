package uk.gov.homeoffice.drt.api

import java.util.Date

import cats.effect.{IO, Sync}
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.dsl.Http4sDsl
import org.http4s.util.CaseInsensitiveString
import org.http4s.{HttpRoutes, Response, Status}
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.coders.ArrivalCoder._
import uk.gov.homeoffice.drt.model.FlightsRequest
import uk.gov.homeoffice.drt.utils.DateUtil.`yyyy-MM-dd_format_toString`

import scala.util.{Failure, Success, Try}

object ArrivalRoutes {

  def arrivalFlightsRoutes[F[_] : Sync](H: ArrivalFlights[F], permissions: List[String]): HttpRoutes[F] = {
    implicit val logger = Slf4jLogger.getLogger[F]
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case req@GET -> Root / "flights" / region :? params =>
        Try {
          val xAuthRoles: List[String] = req.headers.get(CaseInsensitiveString("X-Auth-Roles")).map(_.value.split(",").toList).getOrElse(List.empty)
          val requiredPermissions: Boolean = xAuthRoles.exists(p => permissions.contains(p))
          if (requiredPermissions) {
            val country = params.getOrElse("country", List("Greece")).head
            val date = params.getOrElse("date", List(`yyyy-MM-dd_format_toString`(new Date()))).head
            for {
              arrivals <- H.flights(FlightsRequest(region, country, date))
              resp <- Ok(arrivals)
            } yield resp
          } else {
            Logger[F].warn(s"User logged in does not have valid permission to view the page.") >>
            Response[F](Status.Forbidden)
              .withEntity(s"You need appropriate permissions to view the page.")
              .pure[F]
          }
        } match {
          case Success(r) => r
          case Failure(e) =>
            Logger[F].error(s"Error while request $e") >>
            Response[F](Status.BadRequest)
              .withEntity(s"Bad Request : ${e.getMessage}")
              .pure[F]
        }
    }
  }
}


