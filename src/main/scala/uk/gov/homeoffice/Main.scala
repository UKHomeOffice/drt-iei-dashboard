package uk.gov.homeoffice

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    IEIDashbordServer.stream[IO].compile.drain.as(ExitCode.Success)


}
