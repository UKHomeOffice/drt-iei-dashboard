package uk.gov.homeoffice.service

import uk.gov.homeoffice.Util
import uk.gov.homeoffice.model.{DepartureAirport, RequestedFlightDetails}
import uk.gov.homeoffice.repository.ArrivalRepository

class ArrivalService(arrivalRepository: ArrivalRepository) {

  def getFlightsDetail(requestedDetails: RequestedFlightDetails) = {
    val requestedDate = Util.parseRequestDate(requestedDetails.date)
    val athensCountryAirport = DepartureAirport.getAthensCountryAirport(requestedDetails.country)
    arrivalRepository.arrivalMap.filter(a => athensCountryAirport.exists(_.airportCode == a.origin) && Util.isSameDate(a.scheduledArrivalDate, requestedDate))
  }
}
