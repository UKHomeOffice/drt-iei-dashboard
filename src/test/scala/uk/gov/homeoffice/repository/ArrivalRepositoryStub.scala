package uk.gov.homeoffice.repository

import java.time.LocalDateTime

import cats.effect.{Resource, Sync}
import cats.implicits._
import skunk.Session
import uk.gov.homeoffice.utils.DateUtil

class ArrivalRepositoryStub[F[_] : Sync] extends ArrivalRepositoryI[F] {

  def arrivalMap =
    List(
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        code = "EZY",
        number = 6061,
        destination = "BRA",
        origin = "BRS",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZX",
        number = 6062,
        destination = "BRB",
        origin = "ATH",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZZ",
        number = 6063,
        destination = "BRC",
        origin = "CLJ",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2019-12-21 21:35:00"),
        code = "EZA",
        number = 6064,
        destination = "BRD",
        origin = "CLJ",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZB",
        number = 6065,
        destination = "BRE",
        origin = "CLJ",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZC",
        number = 6066,
        destination = "BRF",
        origin = "CLJ",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZD",
        number = 6067,
        destination = "BRG",
        origin = "CLJ",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZD",
        number = 6067,
        destination = "BRG",
        origin = "SOF",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZD",
        number = 6067,
        destination = "BRG",
        origin = "LCA",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        code = "EZD",
        number = 6067,
        destination = "BRG",
        origin = "ZAG",
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
        status = "ACL Forecast",
        terminal = "T1",
      ),
      ArrivalTableData(
        code = "EZD",
        number = 6067,
        destination = "BRG",
        origin = "LJU",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00")
      ), ArrivalTableData(
        code = "EZD",
        number = 6067,
        origin = "KIV",
        destination = "BRG",
        status = "ACL Forecast",
        terminal = "T1",
        scheduled = DateUtil.parseLocalDateTime("2018-12-21 21:35:00"),
        pcp = DateUtil.parseLocalDateTime("2018-11-23 21:35:00"),
      )
    )


  override def sessionPool: Resource[F, Session[F]] = ???

  override def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]] = arrivalMap.pure[F]
}
