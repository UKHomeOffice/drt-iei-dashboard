package uk.gov.homeoffice.drt.drt

import cats.effect.{ExitCode, IO, IOApp}
import uk.gov.homeoffice.drt.drt.AppEnvironment.config

object Main extends IOApp {
  def run(args: List[String]) =

    config.load[IO].flatMap { cfg =>
      IEIDashbordServer.stream[IO](cfg).compile.drain.as(ExitCode.Success)
    }

}
