package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.syntax.all._
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.{ResourceObject, model}
import uk.gov.homeoffice.drt.model.{Arrival, DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryI, ArrivalTableData}
import uk.gov.homeoffice.drt.utils.DateUtil._
import ResourceObject._
class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F]) {

  private val logger = LoggerFactory.getLogger("uk.gov.homeoffice.drt.service.ArrivalService")

  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = parseLocalDate(requestedDetails.date).atStartOfDay()
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(athensCountryAirport.map(_.airportCode) contains _.origin).zipWithIndex)
  }


  def transformArrivals(arrivalsTableData: F[List[(ArrivalTableData, Int)]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a => model.Arrival((a._2 + 1).toString, dateDefaultTimeZoneConvert(a._1.scheduled), carrierName(a._1.code,a._1.number.toString), a._1.code.toString, a._1.destination, a._1.origin, a._1.scheduled_departure.map(dateAthensTimeZoneConvert(_)))))
  }

  def carrierName(code:String,number:String):String = {
    val stripNumber = number.stripPrefix("0")
    val fsCode = code.stripSuffix(stripNumber).stripSuffix("0")
    logger.debug(s"....fsCode $fsCode code $code number $number")
    getCarrierNameByFS(fsCode).map(_.name).getOrElse(getCarrierNameByICAO(fsCode).map(_.name).getOrElse(""))
  }

}