package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import uk.gov.service.notify.NotificationClient

import java.util
import scala.jdk.CollectionConverters._


class GovNotifyEmailService[F[_] : Sync](apiKey: String) {

  implicit val logger = Slf4jLogger.getLogger[F]

  val client = new NotificationClient(apiKey)

  def accessPersonalisation(requester: String): util.Map[String, String] = {
    Map(
      "requester" -> requester
    ).asJava
  }


  def sendRequest(reference: String, emailAddress: String, templateId: String, personalisation: util.Map[String, String]) = {
    client.sendEmail(templateId,
      emailAddress,
      personalisation,
      reference)
  }
}
