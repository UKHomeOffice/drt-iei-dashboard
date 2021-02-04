package uk.gov.homeoffice.drt

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import uk.gov.homeoffice.drt.AppEnvironment.config
import uk.gov.homeoffice.drt.scheduler.CronScheduler

object Main extends IOApp {
  def run(args: List[String]) = {

    config.load[IO].flatMap { cfg =>
      (IEIDashboardServer.stream[IO](cfg).compile.drain,
        CronScheduler.schedulerTask[IO](cfg).compile.drain).parTupled.as(ExitCode.Success)
    }
  }
}
