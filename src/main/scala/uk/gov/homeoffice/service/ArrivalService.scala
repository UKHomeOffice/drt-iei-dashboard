package uk.gov.homeoffice.service

import java.time.LocalDateTime
import java.util.{Date, UUID}

import cats.effect.Sync
import cats.syntax.all._
import uk.gov.homeoffice.model.{Arrival, DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.repository.{ArrivalTableData, ArrivalsRepository}
import uk.gov.homeoffice.utils.DateUtil

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalsRepository[F]) {

  import java.time.ZoneId

  val defaultZoneId: ZoneId = ZoneId.systemDefault
  val dateConvert: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant())


  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = DateUtil.parseLocalDate(requestedDetails.date).atStartOfDay()
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(athensCountryAirport.map(_.airportCode) contains _.origin))
  }


  def transformArrivals(arrivalsTableData: F[List[ArrivalTableData]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a => Arrival(UUID.randomUUID.toString, dateConvert(a.scheduled), a.code, a.number.toString, a.destination, a.origin, dateConvert(a.pcp))))

  }
}