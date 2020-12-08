package uk.gov.homeoffice

import cats.effect.{Concurrent, ContextShift, Resource}
import natchez.Trace.Implicits.noop
import skunk.Session

object SessionResource {

  def session[F[_] : Concurrent : ContextShift](postgreSQLConfig:PostgreSQLConfig): Resource[F, Session[F]] =
    Session.single[F](
      host = postgreSQLConfig.host,
      port = postgreSQLConfig.port,
      user = postgreSQLConfig.user,
      database = postgreSQLConfig.database,
      password = Some(postgreSQLConfig.password)
    )

}
