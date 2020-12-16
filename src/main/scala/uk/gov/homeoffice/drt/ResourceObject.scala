package uk.gov.homeoffice.drt

import cats.effect.{Concurrent, ContextShift, Resource, Sync}
import cats.syntax.all._
import natchez.Trace.Implicits.noop
import org.slf4j.LoggerFactory
import skunk.Session
import uk.gov.homeoffice.drt.coders.AirlineDecoder
import uk.gov.homeoffice.drt.model.Airlines
import uk.gov.homeoffice.drt.service.AirlineService

import scala.io.Source

object ResourceObject {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def session[F[_] : Concurrent : ContextShift](postgreSQLConfig: PostgreSQLConfig): Resource[F, Session[F]] =
    Session.single[F](
      host = postgreSQLConfig.host,
      port = postgreSQLConfig.port,
      user = postgreSQLConfig.user,
      database = postgreSQLConfig.database,
      password = Some(postgreSQLConfig.password)
    )

  var airlines: Airlines = Airlines(List.empty)

  def updateAirLines[F[_] : Sync : Concurrent : ContextShift](airlineConfig: AirlineConfig, airlineService: AirlineService[F]) = {
    airlineService.getAirlineData(airlineConfig).map(jsonString =>
      AirlineDecoder.airlineJsonDecoder(jsonString) match {
        case Right(a: Airlines) => airlines = a
        case Left(e) => logger.error(s"unable to get airline details")
      }
    )
  }

  def getCarrierNameByFS(fs: String) = airlines.airlines.find(_.fs == fs)

  def getCarrierNameByICAO(icao: String) = airlines.airlines.find(_.icao.getOrElse("") == icao)


  def populateAirlineData = {
    AirlineDecoder.airlinesDecoder(Source.fromResource("airline.json").getLines().mkString) match {
      case Right(a: Airlines) => airlines = a
      case Left(e) => logger.error(s"unable to get airline details ${e.getMessage}")
    }
  }

}
