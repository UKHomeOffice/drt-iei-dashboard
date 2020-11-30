package uk.gov.homeoffice.service

import uk.gov.homeoffice.model.{DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalRepository
import uk.gov.homeoffice.utils.DateUtil

class ArrivalService(arrivalRepository: ArrivalRepository) {

  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = DateUtil.parseRequestDate(requestedDetails.date)
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalRepository.arrivalMap.filter(a => athensCountryAirport.exists(_.airportCode == a.origin) && DateUtil.isSameDate(a.scheduledArrivalDate, requestedDate))
  }
}
