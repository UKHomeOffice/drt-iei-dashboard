package uk.gov.homeoffice.drt.repository

import java.time.LocalDateTime

import cats.effect.{Resource, Sync}
import skunk.codec.all._
import skunk.implicits._
import skunk.{Decoder, Query, Session, ~}

case class ArrivalTableData(code: String,
                            number: Int,
                            destination: String,
                            origin: String,
                            terminal: String,
                            status: String,
                            scheduled: LocalDateTime,
                            scheduled_departure: Option[LocalDateTime]
                           )

trait ArrivalRepositoryI[F[_]] {

  def sessionPool: Resource[F, Session[F]]

  def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]]
}

class ArrivalRepository[F[_] : Sync](val sessionPool: Resource[F, Session[F]]) extends ArrivalRepositoryI[F] {

  val decoder: Decoder[ArrivalTableData] =
    (text ~ int4 ~ text ~ text ~ text ~ text ~ timestamp ~ timestamp.opt).map {
      case code ~ number ~ destination ~ origin ~ terminal ~ status ~ scheduled ~ scheduled_departure =>
        ArrivalTableData(
          code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        )
    }

  private def selectArrivalsForADate: Query[LocalDateTime ~ LocalDateTime, ArrivalTableData] =
    sql"""
        SELECT code , number , destination , origin , terminal , status ,scheduled ,scheduled_departure
        FROM arrival WHERE scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)

  def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]] =
    sessionPool.use { session =>
      session.prepare(selectArrivalsForADate).use { ps =>
        ps.stream(queryDate ~ queryDate.plusDays(1), 1024).compile.toList
      }
    }

}
