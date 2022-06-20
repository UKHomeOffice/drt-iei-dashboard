package uk.gov.homeoffice.drt.api

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.dsl.Http4sDsl
import org.http4s.util.CaseInsensitiveString
import org.http4s.{HttpRoutes, Response, Status}
import uk.gov.homeoffice.drt.applicative.ArrivalFlights
import uk.gov.homeoffice.drt.coders.ArrivalCoder._
import uk.gov.homeoffice.drt.model.{Arrivals, FlightsRequest}


object ArrivalRoutes {

  def arrivalFlightsRoutes[F[_] : Sync](H: ArrivalFlights[F], permissions: List[String]): HttpRoutes[F] = {
    implicit val logger = Slf4jLogger.getLogger[F]
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case req@GET -> Root / "flights" / region / post / departureCountry / filterDate / timezone :? params =>
        val xAuthRoles: List[String] = req.headers.get(CaseInsensitiveString("X-Auth-Roles")).map(_.value.split(",").toList).getOrElse(List.empty)
        val xAuthEmail: List[String] = req.headers.get(CaseInsensitiveString("X-Auth-Email")).map(_.value.split(",").toList).getOrElse(List.empty)
        val requiredPermissions: Boolean = xAuthRoles.exists(p => permissions.contains(p))
        val portList = params.getOrElse("portList", Seq.empty[String]).toList.filter(_.nonEmpty).flatMap(_.split(","))
        val requestString = s"user with email ${xAuthEmail.mkString} request details $region $post $departureCountry ${portList.nonEmpty} $filterDate $timezone"
        if (requiredPermissions) {
          for {
            _ <- Logger[F].info(requestString)
            arrivals <- H.flights(FlightsRequest(region, post, departureCountry, portList, filterDate, timezone))
              .recoverWith {
                case e: Throwable =>
                  Logger[F].warn(s"Error while $requestString : ${e.printStackTrace()}") >>
                    Arrivals(data = List.empty).pure[F]
              }
            resp <- Ok(arrivals)
          } yield resp
        } else {
          Logger[F].warn(s"User with email ${xAuthEmail.mkString} logged in does not have valid permission to view the page. Permissions ${xAuthRoles.mkString}") >>
            Response[F](Status.Forbidden)
              .withEntity(s"You need appropriate permissions to view the page.")
              .pure[F]
        }
    }
  }
}


