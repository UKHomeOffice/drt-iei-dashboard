package uk.gov.homeoffice.drt.service

import cats.effect._
import cats.implicits._
import io.circe.Json
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.client.Client
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.AirlineConfig
import uk.gov.homeoffice.drt.coders.AirlineDecoder
import uk.gov.homeoffice.drt.model.Airlines
import uk.gov.homeoffice.drt.utils.AirlineUtil


class AirlineService[F[_] : Sync](client: Client[F]) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def getAirlineData(airlineConfig: AirlineConfig): F[Json] = {
    client.expect[Json](s"${airlineConfig.endpoint}?appId=${airlineConfig.appId}&appKey=${airlineConfig.appKey}")
  }

  def updateAirLines[F[_] : Sync](airlineConfig: AirlineConfig, airlineService: AirlineService[F]) = {
    getAirlineData(airlineConfig).map(jsonString =>
      AirlineDecoder.airlineJsonDecoder(jsonString) match {
        case Right(a: Airlines) => AirlineUtil.setAirline(a)
        case Left(e) => logger.error(s"unable to get airline details ${e.getMessage}")
      }
    )
  }

}
