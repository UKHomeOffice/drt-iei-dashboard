package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.syntax.all._
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.AppResource._
import uk.gov.homeoffice.drt.model
import uk.gov.homeoffice.drt.model.{Arrival, ArrivalTableDataIndex, DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.drt.repository.ArrivalRepositoryI
import uk.gov.homeoffice.drt.utils.DateUtil._

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F]) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = parseLocalDate(requestedDetails.date).atStartOfDay()
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(athensCountryAirport.map(_.airportCode) contains _.origin).zipWithIndex).map(_.map(a => ArrivalTableDataIndex(a._1, a._2)))

  }


  def transformArrivals(arrivalsTableData: F[List[ArrivalTableDataIndex]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a =>
      model.Arrival((a.index + 1).toString,
        dateDefaultTimeZoneConvert(a.arrivalsTableData.scheduled),
        carrierName(a.arrivalsTableData.code,
          a.arrivalsTableData.number.toString),
        a.arrivalsTableData.code.toString,
        a.arrivalsTableData.destination,
        a.arrivalsTableData.origin,
        a.arrivalsTableData.scheduled_departure.map(dateAthensTimeZoneConvert(_)))))
  }

  def carrierName(code: String, number: String): String = {
    val stripNumber = number.stripPrefix("00")
    val fsCode = code.stripSuffix(stripNumber).stripSuffix("0")
    getCarrierNameByFS(fsCode).map(_.name).getOrElse(getCarrierNameByICAO(fsCode).map(_.name).getOrElse(""))
  }

}