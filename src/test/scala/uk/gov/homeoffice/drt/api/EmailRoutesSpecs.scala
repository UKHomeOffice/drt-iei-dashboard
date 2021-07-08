package uk.gov.homeoffice.drt.api

import cats.effect.IO
import org.http4s.implicits.{http4sKleisliResponseSyntaxOptionT, _}
import org.http4s._
import org.mockito.Mockito.{times, verify}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.homeoffice.drt.service.GovNotifyEmailService
import uk.gov.homeoffice.drt.{BaseSpec, GovNotifyConfig}
import uk.gov.service.notify.NotificationClient

class EmailRoutesSpecs extends AsyncFlatSpec with BaseSpec with Matchers with MockitoSugar {

  val govNotifyConfig = GovNotifyConfig("testApiKey", "testing", "testIEIAccessTemplateId")

  val requester = "test@email.com"

  val mockNotification = mock[NotificationClient]

  val govNotifyService = new GovNotifyEmailService[IO](govNotifyConfig.apiKey) {
    override val client: NotificationClient = mockNotification
  }

  protected val personalisation = govNotifyService.accessPersonalisation(requester)

  "EmailRoutes" should "Send mail to request permission" in {
    val arrivalFlightsResponse = requestPermission(Request[IO](Method.GET, uri"/email/permission", headers = Headers.of(Header("X-Auth-Email", requester))))
    verify(mockNotification, times(1)).sendEmail(govNotifyConfig.ieiAccessTemplateId, "drtpoiseteam@homeoffice.gov.uk", personalisation, govNotifyConfig.reference)
    arrivalFlightsResponse.status mustEqual Status.Ok
    arrivalFlightsResponse.as[String].unsafeRunSync() mustEqual "Email sent"
  }

  private[this] def requestPermission(getHW: Request[IO]): Response[IO] = {
    EmailRoutes.requestPermission[IO](govNotifyService, govNotifyConfig).orNotFound(getHW).unsafeRunSync()
  }

}
