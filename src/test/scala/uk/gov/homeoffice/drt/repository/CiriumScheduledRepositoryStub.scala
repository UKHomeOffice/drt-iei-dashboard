package uk.gov.homeoffice.drt.repository

import java.time.LocalDateTime

import cats.effect.{Resource, Sync}
import skunk.Session

class CiriumScheduledRepositoryStub[F[_] : Sync] extends CiriumScheduledRepositoryI[F] {
  override def sessionPool: Resource[F, Session[F]] = ???

  override def findScheduledDepartureForAFlight(queryDate: LocalDateTime): F[List[ArrivalTableData]] = ???
}
