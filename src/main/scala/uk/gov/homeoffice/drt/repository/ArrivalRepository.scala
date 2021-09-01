package uk.gov.homeoffice.drt.repository

import cats.effect.{Resource, Sync}
import cats.syntax.all._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import skunk.codec.all._
import skunk.data.Completion
import skunk.implicits._
import skunk.{~, _}

import java.time.{LocalDate, LocalDateTime}

case class ArrivalTableData(code: String,
                            number: Int,
                            destination: String,
                            origin: String,
                            terminal: String,
                            status: String,
                            totalPaxNumber: Option[Int],
                            scheduled: LocalDateTime,
                            estimated: Option[LocalDateTime],
                            actual: Option[LocalDateTime],
                            estimatedChox: Option[LocalDateTime],
                            actualChox: Option[LocalDateTime],
                            pcp: Option[LocalDateTime],
                            scheduled_departure: Option[LocalDateTime]
                           )


trait ArrivalRepositoryI[F[_]] {

  def sessionPool: Resource[F, Session[F]]

  def findArrivalsForOriginAndADate(origins: List[String], queryDate: LocalDateTime): F[List[ArrivalTableData]]

  def getArrivalForOriginsWithin3Days(origins: List[String]): F[List[ArrivalTableData]]

  def updateDepartureDate(arrivals: List[ArrivalTableData]): F[List[Completion]]

}

class ArrivalRepository[F[_] : Sync](val sessionPool: Resource[F, Session[F]]) extends ArrivalRepositoryI[F] {

  implicit val logger = Slf4jLogger.getLogger[F]

  val decoder: Decoder[ArrivalTableData] =
    (text ~ int4 ~ text ~ text ~ text ~ text ~ int4.opt ~ timestamp ~ timestamp.opt ~ timestamp.opt ~ timestamp.opt ~ timestamp.opt ~ timestamp.opt ~ timestamp.opt).map {
      case code ~ number ~ destination ~ origin ~ terminal ~ status ~ totalpassengers ~ scheduled ~ estimated ~ actual ~ estimatedchox ~ actualchox ~ pcp ~ scheduled_departure =>
        ArrivalTableData(
          code, number, destination, origin, terminal, status, totalpassengers, scheduled, estimated, actual, estimatedchox, actualchox, pcp, scheduled_departure
        )
    }


  private def selectArrivalsForOriginsAndADate(originListSize: Int): Query[List[String] ~ LocalDateTime ~ LocalDateTime, ArrivalTableData] =
    sql"""
        SELECT code, number, destination, origin, terminal, status, totalpassengers, scheduled, estimated , actual , estimatedchox , actualchox , pcp ,scheduled_departure
        FROM arrival WHERE origin in(${text.list(originListSize)}) and scheduled > $timestamp and scheduled < $timestamp;
       """.query(decoder)

  def findArrivalsForOriginAndADate(origins: List[String], queryDate: LocalDateTime): F[List[ArrivalTableData]] =
    sessionPool.use { session =>
      session.prepare(selectArrivalsForOriginsAndADate(origins.size)).use { ps =>
        ps.stream(origins ~ queryDate ~ queryDate.plusDays(1), 1024).compile.toList
      }
    }

  def getArrivalForOriginsWithin3Days(origins: List[String]): F[List[ArrivalTableData]] = {
    val query: Query[List[String] ~ LocalDateTime ~ LocalDateTime, ArrivalTableData] =
      sql"""
        select code, number, destination, origin, terminal, status, totalpassengers, scheduled, estimated , actual , estimatedchox , actualchox , pcp ,scheduled_departure
        FROM arrival where origin in(${text.list(origins.size)}) and scheduled_departure is NULL and scheduled > $timestamp and scheduled < $timestamp;
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

  def updateDepartureDate(arrivals: List[ArrivalTableData]): F[List[Completion]] = {

    val updateStatus: Command[LocalDateTime ~ LocalDateTime ~ String ~ Int ~ String ~ String] =
      sql"""
         UPDATE arrival
         SET scheduled_departure = $timestamp
         WHERE scheduled = $timestamp and code = $varchar and number = $int4 and destination = $varchar and origin = $varchar;
         """.command

    val arrivalsWithDeparture = arrivals.filter(_.scheduled_departure.isDefined)
    Logger[F].info(s"Updating arrival departure call for size ${arrivalsWithDeparture.size}") >>
      sessionPool.use { session =>
        session.prepare(updateStatus).use { ps =>
          arrivalsWithDeparture.traverse { arrival =>
            Logger[F].info(s"Updating arrival departure scheduled for arrival $arrival") >>
              ps.execute(arrival.scheduled_departure.get ~ arrival.scheduled ~ arrival.code ~ arrival.number ~ arrival.destination ~ arrival.origin)
                .handleErrorWith {
                  case e => Logger[F].info(s"Error while updating $arrival ${e.getMessage} $e") as
                    Completion.Update(0)
                }
          }
        }
      }
  }


}
