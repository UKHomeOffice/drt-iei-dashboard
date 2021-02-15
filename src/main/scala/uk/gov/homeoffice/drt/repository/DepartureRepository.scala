package uk.gov.homeoffice.drt.repository

import java.time.LocalDateTime

import cats.effect.{Resource, Sync}
import cats.syntax.all._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import skunk._
import skunk.codec.all.{int4, text, timestamp, varchar}
import skunk.data.Completion
import skunk.implicits._

case class DepartureTableData(code: String,
                              number: Int,
                              destination: String,
                              origin: String,
                              terminal: String,
                              status: String,
                              scheduled: LocalDateTime,
                              scheduled_departure: LocalDateTime
                             )

trait DepartureRepositoryI[F[_]] {

  def insertDepartureData(ps: List[DepartureTableData]): F[List[Completion]]

  def ignoreScheduledDepartureIfExist(arrivalTableData: ArrivalTableData): F[Option[DepartureTableData]]

  def selectDepartureTableData(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]]

  def selectScheduleDepartureTableWithOutDeparture(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]]
}

class DepartureRepository[F[_] : Sync](val sessionPool: Resource[F, Session[F]]) extends DepartureRepositoryI[F] {

  implicit val logger = Slf4jLogger.getLogger[F]

  val decoderDepartureData: Decoder[DepartureTableData] =
    (text ~ int4 ~ text ~ text ~ text ~ text ~ timestamp ~ timestamp).map {
      case code ~ number ~ destination ~ origin ~ terminal ~ status ~ scheduled ~ scheduled_departure =>
        DepartureTableData(
          code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        )
    }

  val selectScheduleDeparture: Query[Int ~ String ~ String ~ String ~ LocalDateTime ~ LocalDateTime, DepartureTableData] =
    sql"""
        SELECT code , number , destination , origin , terminal , status ,scheduled ,scheduled_departure
        FROM departure WHERE number = $int4 and destination = $varchar and origin =$varchar and terminal = $varchar and scheduled = $timestamp and scheduled_departure = $timestamp
       """.query(decoderDepartureData)

  val selectScheduleDepartureWithOutDeparture: Query[Int ~ String ~ String ~ String ~ LocalDateTime, DepartureTableData] =
    sql"""
        SELECT code , number , destination , origin , terminal , status ,scheduled ,scheduled_departure
        FROM departure WHERE scheduled_departure is not NULL and number = $int4 and destination = $varchar and origin =$varchar and terminal = $varchar and scheduled = $timestamp
       """.query(decoderDepartureData)


  def selectScheduleDepartureTableWithOutDeparture(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = {
    sessionPool.use { session =>
      session.prepare(selectScheduleDepartureWithOutDeparture).use { ps =>
        val a = ps.stream(arrivalTableData.number ~ arrivalTableData.destination ~ arrivalTableData.origin ~ arrivalTableData.terminal ~ arrivalTableData.scheduled, 1024)
        a.compile.toList
      }
    }
  }

  def selectDepartureTableData(arrivalTableData: ArrivalTableData): F[List[DepartureTableData]] = {
    sessionPool.use { session =>
      session.prepare(selectScheduleDeparture).use { ps =>
        val a = ps.stream(arrivalTableData.number ~ arrivalTableData.destination ~ arrivalTableData.origin ~ arrivalTableData.terminal ~ arrivalTableData.scheduled ~ arrivalTableData.scheduled_departure.get, 1024)
        a.compile.toList
      }
    }
  }

  def ignoreScheduledDepartureIfExist(arrivalTableData: ArrivalTableData): F[Option[DepartureTableData]] = {
    val a: F[List[DepartureTableData]] = selectDepartureTableData(arrivalTableData)
    val insertDepartureData: F[Option[DepartureTableData]] = a.map { b =>
      if (b.nonEmpty) {
        logger.debug(s"$arrivalTableData already exists in DepartureTableData")
        None
      } else {
        Some(DepartureTableData(arrivalTableData.code, arrivalTableData.number, arrivalTableData.destination, arrivalTableData.origin, arrivalTableData.terminal, arrivalTableData.status, arrivalTableData.scheduled, arrivalTableData.scheduled_departure.get))
      }
    }

    insertDepartureData
  }

  private def insertCommandDepartureData(p: DepartureTableData): Command[DepartureTableData] = {
    sql"INSERT INTO departure VALUES ($text , $int4 ,$text , $text , $text , $text , $timestamp , $timestamp)".command.gcontramap[DepartureTableData]
  }

  def insertDepartureData(ps: List[DepartureTableData]): F[List[Completion]] =
    ps.traverse { p =>
      sessionPool.use { session =>
        session.prepare(insertCommandDepartureData(p)).use(_.execute(p)).handleErrorWith {
          case e => logger.warn(s"Error while inserting $p ${e.getMessage} $e") as
            Completion.Insert(0)
        }
      }
    }

}
