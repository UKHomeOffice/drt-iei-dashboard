package uk.gov.homeoffice

import cats.effect.{Concurrent, ContextShift, Resource}
import natchez.Trace.Implicits.noop
import skunk.Session

object SessionResource {

  final case class AppResources[F[_]](psql: Resource[F, Session[F]])

  case class PostgreSQLConfig(
                               host: String,
                               port: String,
                               user: String,
                               database: String,
                               max: Int
                             )

  def session[F[_] : Concurrent : ContextShift]: Resource[F, Session[F]] =
    Session.single[F](
      host = "localhost",
      port = 5432,
      user = "drt",
      database = "aggregated",
      password = Some("")
    )

}
