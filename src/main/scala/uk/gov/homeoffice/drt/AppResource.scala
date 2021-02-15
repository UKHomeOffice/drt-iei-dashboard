package uk.gov.homeoffice.drt

import cats.effect.{Concurrent, ConcurrentEffect, ContextShift, Resource}
import cats.syntax.all._
import io.chrisdavenport.log4cats.Logger
import natchez.Trace.Implicits.noop
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import skunk.Session
import scala.concurrent.ExecutionContext


final case class AppResources[F[_]](client: Client[F], psql: Resource[F, Session[F]])

object AppResources {

  private def session[F[_] : Concurrent : ContextShift](postgreSQLConfig: PostgreSQLConfig): Resource[F, Resource[F, Session[F]]] =
    Session
      .pooled[F](
      host = postgreSQLConfig.host,
      port = postgreSQLConfig.port,
      user = postgreSQLConfig.user,
      password = Option(postgreSQLConfig.password),
      database = postgreSQLConfig.database,
      max = postgreSQLConfig.max
    )

  private def mkHttpClient[F[_] : ConcurrentEffect : ContextShift](c: HttpClientConfig): Resource[F, Client[F]] =
    BlazeClientBuilder[F](ExecutionContext.global)
      .withConnectTimeout(c.connectTimeout)
      .withRequestTimeout(c.requestTimeout)
      .resource

  def make[F[_] : ConcurrentEffect : ContextShift : Logger](cfg: Config): Resource[F, AppResources[F]] = {
    (
      mkHttpClient(cfg.httpClient),
      session(cfg.database)
    ).mapN(AppResources.apply[F])

  }


}
