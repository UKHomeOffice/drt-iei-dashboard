package uk.gov.homeoffice.drt.repository

import cats.effect.Sync
import cats.implicits._
import skunk.data.Completion
import uk.gov.homeoffice.drt.utils.DateUtil

class DepartureRepositoryStub[F[_] : Sync] extends DepartureRepositoryI[F] {


  val departureList = List(DepartureTableData(
    code = "BA6068",
    number = 6068,
    origin = "KIV",
    destination = "BRG",
    status = "ACL Forecast",
    terminal = "T1",
    scheduled = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-22 21:35:00"),
    scheduled_departure = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`("2018-12-22 19:35:00")
  ))

  override def insertDepartureData(ps: List[DepartureTableData]): F[List[Completion]] = ???

  override def upsertScheduledDeparture(arrivalTableData: ArrivalTableData): F[Option[DepartureTableData]] = ???

  override def selectDepartureTableData(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = ???

  override def selectScheduleDepartureTableWithOutDeparture(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = {
    departureList.filter(a =>
      arrivalTableData.code == a.code &&
        arrivalTableData.number == a.number &&
        arrivalTableData.origin == a.origin &&
        arrivalTableData.destination == a.destination &&
        arrivalTableData.terminal == a.terminal &&
        arrivalTableData.scheduled == a.scheduled
    ).pure[F]
  }
}
