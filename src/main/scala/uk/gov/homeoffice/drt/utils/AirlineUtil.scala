package uk.gov.homeoffice.drt.utils

import cats.effect.IO
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import uk.gov.homeoffice.drt.coders.AirlineDecoder
import uk.gov.homeoffice.drt.model.Airlines

import scala.io.Source

object AirlineUtil {

  implicit val logger = Slf4jLogger.getLogger[IO]

  private var airlines: Airlines = Airlines(List.empty)

  def setAirline(a: Airlines): Unit = {
    airlines = a
  }

  def getCarrierNameByIData(iata: String) = airlines.airlines.find(a => a.active & a.iata.getOrElse("") == iata)

  def getCarrierNameByICAO(icao: String) = airlines.airlines.find(a => a.active & a.icao.getOrElse("") == icao)

  def populateAirlineData = {
    AirlineDecoder.airlinesDecoder(Source.fromResource("airline.json").getLines().mkString) match {
      case Right(a: Airlines) => setAirline(a)
      case Left(e) => Logger[IO].error(s"unable to get airline details ${e.getMessage}")
    }
  }


}
