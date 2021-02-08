package uk.gov.homeoffice.drt.repository

import java.time.{LocalDate, LocalDateTime}

import cats.effect.{Resource, Sync}
import skunk._
import skunk.codec.all._
import skunk.data.Completion
import skunk.implicits._
import cats.syntax.all._
import org.slf4j.LoggerFactory

case class ArrivalTableData(code: String,
                            number: Int,
                            destination: String,
                            origin: String,
                            terminal: String,
                            status: String,
                            scheduled: LocalDateTime,
                            scheduled_departure: Option[LocalDateTime]
                           )

case class ArrivalScheduledData(code: String,
                                number: Int,
                                destination: String,
                                origin: String,
                                terminal: String,
                                status: String,
                                scheduled: LocalDateTime,
                                scheduled_departure: LocalDateTime
                               )

trait ArrivalRepositoryI[F[_]] {

  def sessionPool: Resource[F, Session[F]]

  def findArrivalsForADate(queryDate: LocalDateTime): F[List[ArrivalTableData]]

  def getArrivalsForOriginAndDate(origin: String): F[List[ArrivalTableData]]

  def insertArrivalScheduledData(ps: List[ArrivalScheduledData]): F[Completion]

  def ignoreScheduledDepartureIfExist(arrivalTableData: ArrivalTableData):F[Option[ArrivalScheduledData]]
}

class ArrivalRepository[F[_] : Sync](val sessionPool: Resource[F, Session[F]]) extends ArrivalRepositoryI[F] {
  private val logger = LoggerFactory.getLogger(getClass.getName)

  val decoder: Decoder[ArrivalTableData] =
    (text ~ int4 ~ text ~ text ~ text ~ text ~ timestamp ~ timestamp.opt).map {
      case code ~ number ~ destination ~ origin ~ terminal ~ status ~ scheduled ~ scheduled_departure =>
        ArrivalTableData(
          code, number, destination, origin, terminal, status, scheduled, scheduled_departure
        )
    }


  val decoderArrivalScheduledData: Decoder[ArrivalScheduledData] =
    (text ~ int4 ~ text ~ text ~ text ~ text ~ timestamp ~ timestamp).map {
      case code ~ number ~ destination ~ origin ~ terminal ~ status ~ scheduled ~ scheduled_departure =>
        ArrivalScheduledData(
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

  val selectAthenOriginArrivalWithin3Days: Query[String ~ LocalDateTime ~ LocalDateTime, ArrivalTableData] =
    sql"""
        SELECT code , number , destination , origin , terminal , status ,scheduled ,scheduled_departure
        FROM arrival WHERE origin = $varchar and scheduled_departure is NULL and scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)


  val selectScheduleArrival: Query[Int ~ String ~ String ~ String ~ LocalDateTime ~ LocalDateTime, ArrivalScheduledData] =
    sql"""
        SELECT code , number , destination , origin , terminal , status ,scheduled ,scheduled_departure
        FROM arrivalScheduled WHERE number = $int4 and destination = $varchar and origin =$varchar and terminal = $varchar and scheduled = $timestamp and scheduled_departure = $timestamp
       """.query(decoderArrivalScheduledData)


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

  def ignoreScheduledDepartureIfExist(arrivalTableData: ArrivalTableData):F[Option[ArrivalScheduledData]] = {
     val a: F[List[ArrivalScheduledData]] =  sessionPool.use { session =>
        session.prepare(selectScheduleArrival).use { ps =>
          val a = ps.stream(arrivalTableData.number ~ arrivalTableData.destination ~ arrivalTableData.origin ~ arrivalTableData.terminal ~ arrivalTableData.scheduled ~ arrivalTableData.scheduled_departure.get, 1024)
          a.compile.toList
        }
      }

    val insertArrivalScheduledData: F[Option[ArrivalScheduledData]] = a.map { b =>
      if(b.nonEmpty) {
        None
      } else{
        Some(ArrivalScheduledData(arrivalTableData.code,arrivalTableData.number,arrivalTableData.destination,arrivalTableData.origin,arrivalTableData.terminal,arrivalTableData.status,arrivalTableData.scheduled,arrivalTableData.scheduled_departure.get))
      }
    }

    insertArrivalScheduledData
  }

  private def insertCommandArrivalScheduledData(ps: List[ArrivalScheduledData]): Command[ps.type] = {
    val enc = (text ~ int4 ~ text ~ text ~ text ~ text ~ timestamp ~ timestamp).gcontramap[ArrivalScheduledData].values.list(ps)
    sql"INSERT INTO arrivalScheduled VALUES $enc".command
  }

  def insertArrivalScheduledData(ps: List[ArrivalScheduledData]): F[Completion] =

    sessionPool.use { session =>
      session.prepare(insertCommandArrivalScheduledData(ps)).use(_.execute(ps))
    }
}
