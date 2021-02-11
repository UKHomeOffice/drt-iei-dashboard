package uk.gov.homeoffice.drt.scheduler

import cats.effect.{ConcurrentEffect, ContextShift, Resource, Timer}
import cron4s.Cron
import eu.timepit.fs2cron.awakeEveryCron
import fs2.Stream
import org.http4s.client.Client
import skunk.Session
import uk.gov.homeoffice.drt.Config
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, ArrivalTableData, DepartureRepository}
import uk.gov.homeoffice.drt.service.{CiriumService, FlightScheduledService}

object CronScheduler {

  def schedulerTask[F[_] : ConcurrentEffect](cfg: Config, client: Client[F], session: Resource[F, Session[F]])(implicit timer: Timer[F], C: ContextShift[F]) = {
    val cronSchedulerConfig = Cron.unsafeParse(cfg.cronJob.scheduler)
    val arrivalsService: FlightScheduledService[F] = new FlightScheduledService(new ArrivalRepository(session), new DepartureRepository(session))

    val ciriumService = new CiriumService(cfg.airline, client, cfg.cronJob.ciriumSchedulesEndpoint)

    awakeEveryCron(cronSchedulerConfig) >> Stream.eval {
      val arrivalTableDatas: F[List[ArrivalTableData]] = arrivalsService.getScheduledDeparture
      val amendArrivalTableDatas: F[List[ArrivalTableData]] = ciriumService.appendScheduledDeparture(arrivalTableDatas)
      arrivalsService.insertDepartureTableData(amendArrivalTableDatas)
    }
  }

}