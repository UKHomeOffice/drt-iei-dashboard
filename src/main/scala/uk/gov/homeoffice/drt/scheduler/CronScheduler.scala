package uk.gov.homeoffice.drt.scheduler

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cron4s.Cron
import eu.timepit.fs2cron.awakeEveryCron
import fs2.Stream
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, ArrivalTableData, DepartureRepository}
import uk.gov.homeoffice.drt.service.{FlightScheduledService, CiriumService}
import uk.gov.homeoffice.drt.{AppResource, Config}

object CronScheduler {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def schedulerTask[F[_] : ConcurrentEffect](cfg: Config)(implicit timer: Timer[F], C: ContextShift[F]) = {
    val cronSchedulerConfig = Cron.unsafeParse(cfg.cronJob.scheduler)
    val session = AppResource.session(cfg.database)
    val clientResource = AppResource.mkHttpClient(cfg.httpClient)

    val arrivalsService: FlightScheduledService[F] = new FlightScheduledService(new ArrivalRepository(session), new DepartureRepository(session))

    val ciriumService = new CiriumService(cfg.airline, clientResource, cfg.cronJob.ciriumSchedulesEndpoint)

    awakeEveryCron(cronSchedulerConfig) >> Stream.eval {
      val arrivalTableDatas: F[List[ArrivalTableData]] = arrivalsService.getScheduledDeparture
      val amendArrivalTableDatas: F[List[ArrivalTableData]] = ciriumService.appendScheduledDeparture(arrivalTableDatas)
      arrivalsService.insertDepartureTableData(amendArrivalTableDatas)
    }
  }


}