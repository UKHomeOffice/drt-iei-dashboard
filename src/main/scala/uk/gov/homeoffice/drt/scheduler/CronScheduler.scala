package uk.gov.homeoffice.drt.scheduler

import java.time.LocalTime

import cats.effect.{Concurrent, ContextShift, IO, Timer}
import cron4s.Cron
import eu.timepit.fs2cron.awakeEveryCron
import fs2.Stream
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.repository.ArrivalRepository
import uk.gov.homeoffice.drt.service.ArrivalService
import uk.gov.homeoffice.drt.{AppResource, Config}

object CronScheduler {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  val even15Seconds = Cron.unsafeParse("*/15 * * ? * *")
  // evenSeconds: cron4s.CronExpr = */2 * * ? * *

  val printTime = Stream.eval(IO(println(LocalTime.now)))
  // printTime: fs2.Stream[cats.effect.IO,Unit] = Stream(..)

//  def scheduled[F[_] : Concurrent](arrivalsService: ArrivalService[F])(implicit timer: Timer[F]): Stream[F, Seq[Unit]] = awakeEveryCron(even15Seconds) >> Stream.eval(arrivalsService.updateScheduledDeparture)


  def schedulerTask[F[_] : Concurrent](cfg: Config)(implicit timer: Timer[F], C: ContextShift[F]) = {
    val session = AppResource.session(cfg.database)

    val arrivalsService: ArrivalService[F] = new ArrivalService(new ArrivalRepository(session))

    awakeEveryCron(even15Seconds) >> Stream.eval(arrivalsService.updateScheduledDeparture)
  }


}