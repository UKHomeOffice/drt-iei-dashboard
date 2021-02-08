package uk.gov.homeoffice.drt.scheduler

import java.time.LocalTime

import cats.effect.{Concurrent, ConcurrentEffect, ContextShift, IO, Sync, Timer}
import cron4s.Cron
import eu.timepit.fs2cron.awakeEveryCron
import fs2.Stream
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, ArrivalTableData, CiriumScheduledRepository, DepartureRepository}
import uk.gov.homeoffice.drt.service.{ArrivalService, CiriumService}
import uk.gov.homeoffice.drt.{AppResource, Config}

object CronScheduler {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  val even15Seconds = Cron.unsafeParse("0 */2 * ? * *")

//  val printTime = Stream.eval(IO(println(LocalTime.now)))


  def schedulerTask[F[_]  : ConcurrentEffect](cfg: Config)(implicit timer: Timer[F], C: ContextShift[F]) = {
    val session = AppResource.session(cfg.database)
    val clientResource = AppResource.mkHttpClient(cfg.httpClient)

    val arrivalsService: ArrivalService[F] = new ArrivalService(new ArrivalRepository(session),new DepartureRepository(session))

    val ciriumService = new CiriumService(new CiriumScheduledRepository(session), cfg.airline, clientResource)

    awakeEveryCron(even15Seconds) >> Stream.eval{
      val arrivalTableDatas: F[List[ArrivalTableData]] = arrivalsService.getScheduledDeparture
      val amendArrivalTableDatas: F[List[ArrivalTableData]] = ciriumService.appendScheduledDeparture(arrivalTableDatas)
      arrivalsService.insertUpdateDepartureTableData(amendArrivalTableDatas)
      }
    }




}