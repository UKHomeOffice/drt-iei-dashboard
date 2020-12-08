package uk.gov.homeoffice

import cats.implicits._
import ciris.refined._
import ciris.{env, _}
import eu.timepit.refined.auto._
import eu.timepit.refined.types.net.UserPortNumber
import eu.timepit.refined.types.string.NonEmptyString


object AppEnvironment {

  def apiConfig: ConfigValue[ApiConfig] = (
    env("API_PORT").or(prop("api.port")).as[UserPortNumber].option,
    env("APP_ENV").as[String].option).parMapN { (port, env) =>
    ApiConfig(
      port = port getOrElse 9001,
      env = env
    )
  }

  val databaseConfig: ConfigValue[PostgreSQLConfig] = (
      env("PG_DATABASE_HOST").as[String].default("localhost"),
      env("PG_DATABASE_PORT").as[Int].default(5432),
      env("PG_DATABASE_USERNAME").as[String].default("drt"),
      env("PG_DATABASE_PASSWORD").as[String].default(""),
      env("PG_DATABASE").as[String].default("aggregated"),
      env("PG_DATABASE_SESSION_POOL_MAX").as[Int].default(10)
    ).parMapN(PostgreSQLConfig)


  val config: ConfigValue[Config] = (
      apiConfig,
      databaseConfig
    ).parMapN { (api, database) =>
      Config(
        appName = "IEI-Dashboard",
        api = api,
        database = database
      )
    }

}


final case class ApiConfig(port: UserPortNumber, env: Option[String])

final case class Config(appName: NonEmptyString, api: ApiConfig, database: PostgreSQLConfig)

final case class PostgreSQLConfig(host: String, port: Int, user: String, password: String, database: String, max: Int)