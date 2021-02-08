package uk.gov.homeoffice.drt.repository

import cats.effect.Sync
import skunk.data.Completion

class DepartureRepositoryStub[F[_] : Sync] extends DepartureRepositoryI[F] {
  override def insertDepartureData(ps: List[DepartureTableData]): F[List[Completion]] = ???

  override def ignoreScheduledDepartureIfExist(arrivalTableData: ArrivalTableData): F[Option[DepartureTableData]] = ???

  override def selectDepartureTableData(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = ???

  override def selectScheduleDepartureTableWithOutDeparture(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = ???
}
