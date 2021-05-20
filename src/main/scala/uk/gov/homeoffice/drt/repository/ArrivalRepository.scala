package uk.gov.homeoffice.drt.repository

import cats.effect.{Resource, Sync}
import skunk._
import skunk.codec.all._
import skunk.implicits._

import java.time.{LocalDate, LocalDateTime}

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

  def getArrivalsForOriginAndDate(origin: String): F[List[ArrivalTableData]]

  def getArrivalForListOriginAndDate(origins: List[String]): F[List[ArrivalTableData]]
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
        SELECT code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        FROM arrival WHERE scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)

  def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]] =
    sessionPool.use { session =>
      session.prepare(selectArrivalsForADate).use { ps =>
        ps.stream(queryDate ~ queryDate.plusDays(1), 1024).compile.toList
      }
    }

  val selectAthenOriginArrivalWithin3Days: Query[String ~ LocalDateTime ~ LocalDateTime, ArrivalTableData] =
    sql"""
        SELECT code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        FROM arrival WHERE origin = $varchar and scheduled_departure is NULL and scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)


  def getArrivalForListOriginAndDate(origins: List[String]): F[List[ArrivalTableData]] = {
    val query : Query[List[String] ~ LocalDateTime ~ LocalDateTime, ArrivalTableData] =
      sql"""
        select code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        FROM arrival where origin in(${text.list(origins.size)})and scheduled_departure is NULL and scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)

    val currentDate: LocalDateTime = LocalDate.now().atStartOfDay()
    val currentDatePlus3Days: LocalDateTime = currentDate.plusDays(3)
    sessionPool.use { session =>
      session.prepare(query).use { ps =>
        val a = ps.stream(origins ~ currentDate ~ currentDatePlus3Days, 1024)
        a.compile.toList
      }
    }
  }

  def getArrivalsForOriginAndDate(origin: String): F[List[ArrivalTableData]] = {

    val currentDate: LocalDateTime = LocalDate.now().atStartOfDay()
    val currentDatePlus3Days: LocalDateTime = currentDate.plusDays(3)
    sessionPool.use { session =>
      session.prepare(selectAthenOriginArrivalWithin3Days).use { ps =>
        val a = ps.stream(origin ~ currentDate ~ currentDatePlus3Days, 1024)
        a.compile.toList
      }
    }
  }


}
