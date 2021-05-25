package uk.gov.homeoffice.drt.repository

import java.time.LocalDateTime
import cats.effect.{Resource, Sync}
import cats.implicits._
import skunk.Session
import skunk.data.Completion
import uk.gov.homeoffice.drt.utils.DateUtil

class ArrivalRepositoryStub[F[_] : Sync] extends ArrivalRepositoryI[F] {

  def arrivalMap =
    List(
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00"),
        code = "BA6061",
        number = 6061,
        destination = "BRA",
        origin = "BRS",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "EZX6062",
        number = 6062,
        destination = "BRB",
        origin = "ATH",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "EZZ6063",
        number = 6063,
        destination = "BRC",
        origin = "CLJ",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2019-12-21 21:35:00"),
        code = "EZA6064",
        number = 6064,
        destination = "BRD",
        origin = "CLJ",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "EZB6065",
        number = 6065,
        destination = "BRE",
        origin = "CLJ",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "EZC6066",
        number = 6066,
        destination = "BRF",
        origin = "CLJ",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "BA6067",
        number = 6067,
        destination = "BRG",
        origin = "CLJ",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "BA6067",
        number = 6067,
        destination = "BRG",
        origin = "SOF",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "BA6067",
        number = 6067,
        destination = "BRG",
        origin = "LCA",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        code = "BA6067",
        number = 6067,
        destination = "BRG",
        origin = "ZAG",
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        code = "BA6067",
        number = 6067,
        destination = "BRG",
        origin = "LJU",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00"))
      ), ArrivalTableData(
        code = "BA6067",
        number = 6067,
        origin = "KIV",
        destination = "BRG",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-21 21:35:00"),
        scheduled_departure = Some(DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-11-23 21:35:00")),
      ), ArrivalTableData(
        code = "BA6068",
        number = 6068,
        origin = "KIV",
        destination = "BRG",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-22 21:35:00"),
        scheduled_departure = None,
      ), ArrivalTableData(
        code = "BA6069",
        number = 6069,
        origin = "KIV",
        destination = "BRG",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-23 21:35:00"),
        scheduled_departure = None,
      )
    )


  override def sessionPool: Resource[F, Session[F]] = ???

  override def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]] = {
    arrivalMap.filter(a => a.scheduled.toLocalDate == queryDate.toLocalDate).pure[F]
  }

  override def getArrivalsForOriginAndDate(origin: String): F[List[ArrivalTableData]] = ???

  override def getArrivalForListOriginAndDate(origins: List[String]): F[List[ArrivalTableData]] = ???

  override def updateDepartureDate(arrivals: List[ArrivalTableData]): F[List[Completion]] = ???


}
