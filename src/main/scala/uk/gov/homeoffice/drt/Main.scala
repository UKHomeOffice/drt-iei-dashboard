package uk.gov.homeoffice.drt

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import uk.gov.homeoffice.drt.AppEnvironment.config
import uk.gov.homeoffice.drt.scheduler.CronScheduler
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {
  def run(args: List[String]) = {
    implicit val logger = Slf4jLogger.getLogger[IO]
    config.load[IO].flatMap { cfg =>
      Logger[IO].info(s"Loaded config $cfg") >>
      (IEIDashboardServer.stream[IO](cfg).compile.drain,
        CronScheduler.schedulerTask[IO](cfg).compile.drain).parTupled.as(ExitCode.Success)
    }
  }
}
