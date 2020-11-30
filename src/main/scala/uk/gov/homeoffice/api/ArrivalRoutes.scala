package uk.gov.homeoffice.api

import java.util.Date

import cats.effect.Sync
import cats.implicits._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpRoutes, Response, Status}
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.applicative.ArrivalFlights
import uk.gov.homeoffice.coders.ArrivalCoder._
import uk.gov.homeoffice.model.RequestedFlightDetails
import uk.gov.homeoffice.utils.DateUtil

import scala.util.{Failure, Success, Try}

object ArrivalRoutes {

  private final val logger = LoggerFactory.getLogger("uk.gov.homeoffice.api.ArrivalRoutes");

  //http://localhost:8080/flights/athens?country=Greece&date=2018-12-12

  def arrivalFlightsRoutes[F[_] : Sync](H: ArrivalFlights[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "flights" / region :? params =>
        Try {
          val country = params.getOrElse("country", List("Greece")).head
          val date = params.getOrElse("date", List(DateUtil.formatRequestDate(new Date()))).head
          for {
            arrivals <- H.flights(RequestedFlightDetails(region, country, date))
            resp <- Ok(arrivals)
          } yield resp
        } match {
          case Success(r) => r
          case Failure(e) =>
            logger.error(s"Error while request", e)
            Response[F](Status.BadRequest)
              .withEntity(s"Bad Request : ${e.getMessage}")
              .pure[F]
        }
    }
  }
}


