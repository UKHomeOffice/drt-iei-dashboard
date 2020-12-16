package uk.gov.homeoffice.drt.service

import cats.effect._
import io.circe.Json
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.client.Client
import org.slf4j.LoggerFactory



class AirlineService[F[_] : Sync](clientResource: Resource[F, Client[F]]) {

  private val logger = LoggerFactory.getLogger("uk.gov.homeoffice.drt.service.AirlineService")

  def getAirlineData: F[Json] =  {
    clientResource.use { client =>
      client.expect[Json]("https://api.flightstats.com/flex/airlines/rest/v1/json/all?appId=5986a673&appKey=af92b9e2bd2a574783efc35fed038bc4")
    }
  }
}
