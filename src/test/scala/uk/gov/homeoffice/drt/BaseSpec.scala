package uk.gov.homeoffice.drt

import cats.effect.{ContextShift, IO}

import scala.concurrent.ExecutionContext

trait BaseSpec {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
  //  implicit val timer: Timer[IO]     = IO.timer(ExecutionContext.global)
  //  implicit val concurrentEffect: ConcurrentEffect[IO] = IO.ioConcurrentEffect
}
