package uk.gov.homeoffice.drt.api

import cats.effect.Sync
import cats.implicits.catsSyntaxApplicativeId
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.dsl.Http4sDsl
import org.http4s.util.CaseInsensitiveString
import org.http4s.{HttpRoutes, Response, Status}
import uk.gov.homeoffice.drt.GovNotifyConfig
import uk.gov.homeoffice.drt.service.GovNotifyEmailService
import cats.implicits._

object EmailRoutes {
  def requestPermission[F[_] : Sync](govNotifyEmail: GovNotifyEmailService[F], govNotifyConfig: GovNotifyConfig): HttpRoutes[F] = {
    implicit val logger = Slf4jLogger.getLogger[F]
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case req@GET -> Root / "email" / "permission" =>
        val xAuthRoles: List[String] = req.headers.get(CaseInsensitiveString("X-Auth-Roles")).map(_.value.split(",").toList).getOrElse(List.empty)
        val xAuthEmail: List[String] = req.headers.get(CaseInsensitiveString("X-Auth-Email")).map(_.value.split(",").toList).getOrElse(List.empty)
        Logger[F].info(s"Request details email permission for user $xAuthEmail with existing roles $xAuthRoles") >>
          Sync[F].delay {
            val personalisation = govNotifyEmail.accessPersonalisation(xAuthEmail.headOption.getOrElse(""))
            govNotifyEmail.sendRequest(govNotifyConfig.reference, "drtpoiseteam@homeoffice.gov.uk", govNotifyConfig.ieiAccessTemplateId, personalisation)
          }.attempt.flatMap {
            case Left(e) => Logger[F].error(e)("Unable to send email request") >>
              Response[F](Status.BadRequest)
                .withEntity(s"Bad Request : ${e.getMessage}")
                .pure[F]
            case Right(_) => Response[F](Status.Ok)
              .withEntity(s"Email sent")
              .pure[F]
          }
    }
  }
}
