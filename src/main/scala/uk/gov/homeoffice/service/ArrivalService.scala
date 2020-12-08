package uk.gov.homeoffice.service

import java.time.LocalDateTime
import java.util.Date

import cats.effect.Sync
import cats.syntax.all._
import uk.gov.homeoffice.model.{Arrival, DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.repository.{ArrivalRepositoryI, ArrivalTableData}
import uk.gov.homeoffice.utils.DateUtil

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F]) {

  import java.time.ZoneId

  val defaultZoneId: ZoneId = ZoneId.systemDefault
  val dateConvert: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant())


  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = DateUtil.parseLocalDate(requestedDetails.date).atStartOfDay()
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(athensCountryAirport.map(_.airportCode) contains _.origin).zipWithIndex)
  }


  def transformArrivals(arrivalsTableData: F[List[(ArrivalTableData, Int)]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a => Arrival((a._2 + 1).toString, dateConvert(a._1.scheduled), a._1.code, a._1.number.toString, a._1.destination, a._1.origin, a._1.scheduled_departure.map(dateConvert(_)))))

  }
}