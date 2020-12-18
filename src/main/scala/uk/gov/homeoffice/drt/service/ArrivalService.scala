package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.syntax.all._
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.AppResource._
import uk.gov.homeoffice.drt.model
import uk.gov.homeoffice.drt.model.{Arrival, ArrivalTableDataIndex, DepartureAirport, FlightsRequest}
import uk.gov.homeoffice.drt.repository.ArrivalRepositoryI
import uk.gov.homeoffice.drt.utils.DateUtil._

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F]) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def getFlightsDetail(requestedDetails: FlightsRequest) = {
    val requestedDate = `yyyy-MM-dd_parse_toLocalDate`(requestedDetails.date).atStartOfDay()
    val portList = DepartureAirport.athensDeparturePortsForCountry(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(portList.map(_.code) contains _.origin).zipWithIndex).map(_.map(a => ArrivalTableDataIndex(a._1, a._2)))

  }


  def transformArrivals(arrivalsTableData: F[List[ArrivalTableDataIndex]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a =>
      model.Arrival((a.index + 1).toString,
        UTCTimeZoneConvertDate(a.arrivalsTableData.scheduled),
        carrierName(a.arrivalsTableData.code,
          a.arrivalsTableData.number.toString),
        a.arrivalsTableData.code.toString,
        a.arrivalsTableData.destination,
        a.arrivalsTableData.origin,
        a.arrivalsTableData.scheduled_departure.map(`UTC+2TimeZoneConvertDate`(_)))))
  }

  def carrierName(code: String, number: String): String = {
    val iataCode = code.stripSuffix(number).split('0').headOption.getOrElse("")
    getCarrierNameByIData(iataCode).map(_.name).getOrElse(getCarrierNameByICAO(iataCode).map(_.name).getOrElse(""))
  }

}