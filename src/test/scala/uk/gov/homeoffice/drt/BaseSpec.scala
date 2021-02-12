package uk.gov.homeoffice.drt

import cats.effect.{ContextShift, IO}

import scala.concurrent.ExecutionContext

trait BaseSpec {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
}
