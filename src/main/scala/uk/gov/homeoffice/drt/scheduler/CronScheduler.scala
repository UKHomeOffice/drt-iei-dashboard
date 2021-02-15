package uk.gov.homeoffice.drt.scheduler

import cats.effect.{ConcurrentEffect, ContextShift, Resource, Timer}
import cron4s.Cron
import eu.timepit.fs2cron.awakeEveryCron
import fs2.Stream
import io.chrisdavenport.log4cats.Logger
import org.http4s.client.Client
import skunk.Session
import uk.gov.homeoffice.drt.Config
import uk.gov.homeoffice.drt.repository.{ArrivalRepository, ArrivalTableData, DepartureRepository}
import uk.gov.homeoffice.drt.service.{CiriumService, FlightScheduledService}

object CronScheduler {

  def schedulerTask[F[_] : ConcurrentEffect : Logger](cfg: Config, client: Client[F], session: Resource[F, Session[F]])(implicit timer: Timer[F], C: ContextShift[F]) = {
    val cronSchedulerConfig = Cron.unsafeParse(cfg.cronJob.scheduler)
    val arrivalsService: FlightScheduledService[F] = new FlightScheduledService(new ArrivalRepository(session), new DepartureRepository(session))

    val ciriumService = new CiriumService(cfg.airline, client, cfg.cronJob.ciriumSchedulesEndpoint)

    awakeEveryCron(cronSchedulerConfig) >> Stream.eval {
      val arrivalTableDataList: F[List[ArrivalTableData]] = arrivalsService.getScheduledDeparture
      val amendArrivalTableDataList: F[List[ArrivalTableData]] = ciriumService.appendScheduledDeparture(arrivalTableDataList)
      arrivalsService.insertDepartureTableData(amendArrivalTableDataList)
    }
  }

}