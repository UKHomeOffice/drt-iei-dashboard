package uk.gov.homeoffice.drt

import cats.effect.{ExitCode, IO, IOApp}
import AppEnvironment.config

object Main extends IOApp {
  def run(args: List[String]) =

    config.load[IO].flatMap { cfg =>
      IEIDashboardServer.stream[IO](cfg).compile.drain.as(ExitCode.Success)
    }

}
