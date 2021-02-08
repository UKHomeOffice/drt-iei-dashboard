package uk.gov.homeoffice.drt

import cats.implicits._
import ciris.refined._
import ciris.{env, _}
import eu.timepit.refined.auto._
import eu.timepit.refined.types.net.UserPortNumber
import eu.timepit.refined.types.string.NonEmptyString

import scala.concurrent.duration._


object AppEnvironment {

  def apiConfig: ConfigValue[ApiConfig] = (
    env("API_PORT").or(prop("api.port")).as[UserPortNumber].option,
    env("API_PERMISSIONS").as[String].default("iei-dashboard:view"),
    env("APP_ENV").as[String].option).parMapN { (port, permissions, env) =>
    ApiConfig(
      port = port getOrElse 9001,
      permissions = permissions.split(",").toList,
      env = env
    )
  }

  val airlineConfig: ConfigValue[AirlineConfig] = (
    env("CIRIUM_API_ENDPOINT").as[String].default("https://api.flightstats.com/flex/airlines/rest/v1/json/all"),
    env("CIRIUM_API_APPID").as[String].default(""),
    env("CIRIUM_API_APPKEY").as[String].default("")).parMapN { (endpoint, appId, appKey) =>
    AirlineConfig(
      endpoint = endpoint,
      appId = appId,
      appKey = appKey
    )
  }

  val databaseConfig: ConfigValue[PostgreSQLConfig] = (
    env("AGGDB_HOST").as[String].default("localhost"),
    env("AGGDB_PORT").as[Int].default(5432),
    env("AGGDB_USER").as[String].default("drt"),
    env("AGGDB_PASSWORD").as[String].default("drt"),
    env("AGGDB_DATABASE").as[String].default("aggregated"),
    env("AGGDB_SESSION_POOL_MAX").as[Int].default(10)
  ).parMapN(PostgreSQLConfig)

  val httpClientConfig: ConfigValue[HttpClientConfig] = (
    env("HTTP_CLIENT_CONNECTION_TIMEOUT").as[Int].default(2),
    env("HTTP_CLIENT_REQUEST_TIMEOUT").as[Int].default(2)
  ).parMapN { (connectTimeout, requestTimeout) =>
    HttpClientConfig(connectTimeout seconds, requestTimeout seconds)
  }

  val cronJobConfig: ConfigValue[CronJobConfig] = (
    env("CIRIUM_SCHEDULES_ENDPOINT").as[String].default("https://api.flightstats.com/flex/schedules/rest/v1/json/flight/"),
    env("CRON_SCHEDULER_TIMER").as[String].default("0 */2 * ? * *")
  ).parMapN { (endpoint, scheduler) =>
    CronJobConfig(endpoint, scheduler)
  }

  val config: ConfigValue[Config] = (
    apiConfig,
    databaseConfig,
    airlineConfig,
    httpClientConfig,
    cronJobConfig
  ).parMapN { (api, database, airline, httpClient, cronjob) =>
    Config(
      appName = "IEI-Dashboard",
      api = api,
      database = database,
      airline = airline,
      httpClient = httpClient,
      cronJob = cronjob
    )
  }

}

final case class CronJobConfig(ciriumSchedulesEndpoint: String, scheduler: String)

final case class AirlineConfig(endpoint: String, appId: String, appKey: String)

final case class ApiConfig(port: UserPortNumber, env: Option[String], permissions: List[String])

final case class Config(appName: NonEmptyString, api: ApiConfig, database: PostgreSQLConfig, airline: AirlineConfig, httpClient: HttpClientConfig, cronJob: CronJobConfig)

final case class PostgreSQLConfig(host: String, port: Int, user: String, password: String, database: String, max: Int)

final case class HttpClientConfig(connectTimeout: FiniteDuration, requestTimeout: FiniteDuration)