package uk.gov.homeoffice.drt.service

import cats.effect.{Effect, IO, Sync}
import cats.implicits._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.{HttpRoutes, Response, Status}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import uk.gov.homeoffice.drt.AirlineConfig
import uk.gov.homeoffice.drt.coders.CiriumCoder._
import uk.gov.homeoffice.drt.model.{CiriumScheduledFlights, CiriumScheduledResponse}
import uk.gov.homeoffice.drt.repository.ArrivalTableData
import uk.gov.homeoffice.drt.utils.DateUtil

class FakeCiriumRoute[F[_] : Sync] extends Http4sDsl[F] {

  def service(implicit F: Effect[F]): HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / uri / flight / number / "departing" / year / month / day :? params =>
      Response[F](status = Status.Ok).withEntity(
        CiriumScheduledResponse(
          List(CiriumScheduledFlights("BA", "6069", "ABC", "XYZ", "2018-12-23T17:35:00.000", "2018-12-23T21:35:00.000"))
        ).asJson).pure[F]
  }
}


class CiriumServiceSpecs extends AsyncFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  val client: Client[IO] = Client.fromHttpApp[IO](new FakeCiriumRoute[IO]().service.orNotFound)

  val ciriumService = new CiriumService[IO](AirlineConfig("a", "b", "c"), client, schedulerEndpoint = "a/")

  "Cirium api" should "return scheduled departure" in {

    val requestedArrivalTableData = List(ArrivalTableData(
      code = "BA6069",
      number = 6069,
      origin = "KIV",
      destination = "BRG",
      status = "ACL Forecast",
      terminal = "T1",
      scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-23 21:35:00"),
      scheduled_departure = None,
      totalPaxNumber = Some(10)
    ))

    val expectedArrivalTableData = List(ArrivalTableData(
      code = "BA6069",
      number = 6069,
      origin = "KIV",
      destination = "BRG",
      status = "ACL Forecast",
      terminal = "T1",
      scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-23 21:35:00"),
      scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-23 17:35:00")),
      totalPaxNumber = Some(10)
    ))

    val result: List[ArrivalTableData] = ciriumService.appendScheduledDeparture(requestedArrivalTableData.pure[IO]).unsafeRunSync()

    expectedArrivalTableData mustEqual result

  }


}
