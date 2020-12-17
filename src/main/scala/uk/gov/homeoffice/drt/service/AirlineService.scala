package uk.gov.homeoffice.drt.service

import cats.effect._
import io.circe.Json
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.client.Client
import uk.gov.homeoffice.drt.AirlineConfig


class AirlineService[F[_] : Sync](clientResource: Resource[F, Client[F]]) {

  def getAirlineData(airlineConfig: AirlineConfig): F[Json] = {
    clientResource.use { client =>
      client.expect[Json](s"${airlineConfig.endpoint}?appId=${airlineConfig.appId}&appKey=${airlineConfig.appKey}")
    }
  }
}
