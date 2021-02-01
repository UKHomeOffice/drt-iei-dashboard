package uk.gov.homeoffice.drt.repository

import java.time.LocalDateTime

import cats.effect.{Resource, Sync}
import skunk.Session


trait CiriumScheduledRepositoryI[F[_]] {

  def sessionPool: Resource[F, Session[F]]

  def findScheduledDepartureForAFlight(queryDate: LocalDateTime): F[List[ArrivalTableData]]
}

class CiriumScheduledRepository[F[_] : Sync](val sessionPool: Resource[F, Session[F]]) extends CiriumScheduledRepositoryI[F] {
  override def findScheduledDepartureForAFlight(queryDate: LocalDateTime): F[List[ArrivalTableData]] = ???
}