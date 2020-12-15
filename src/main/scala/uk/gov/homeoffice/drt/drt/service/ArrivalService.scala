package uk.gov.homeoffice.drt.drt.service

import cats.effect.Sync
import cats.syntax.all._
import uk.gov.homeoffice.drt.drt.model.{Arrival, DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.drt.drt.repository.{ArrivalRepositoryI, ArrivalTableData}
import uk.gov.homeoffice.drt.drt.utils.DateUtil._

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F]) {


  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = parseLocalDate(requestedDetails.date).atStartOfDay()
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(athensCountryAirport.map(_.airportCode) contains _.origin).zipWithIndex)
  }


  def transformArrivals(arrivalsTableData: F[List[(ArrivalTableData, Int)]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a => Arrival((a._2 + 1).toString, dateDefaultTimeZoneConvert(a._1.scheduled), a._1.code, a._1.number.toString, a._1.destination, a._1.origin, a._1.scheduled_departure.map(dateAthensTimeZoneConvert(_)))))

  }
}