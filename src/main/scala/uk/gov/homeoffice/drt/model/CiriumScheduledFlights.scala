package uk.gov.homeoffice.drt.model

import scala.util.control.NoStackTrace

case class CiriumScheduledFlights(carrierFsCode: String,
                                  flightNumber: String,
                                  departureAirportFsCode: String,
                                  arrivalAirportFsCode: String,
                                  departureTime: String,
                                  arrivalTime: String)

case class CiriumScheduledResponse(scheduledFlights: Seq[CiriumScheduledFlights])

case class CiriumScheduledFlightRequest(flightCode: String,
                                        flightNumber: Int,
                                        year: Int,
                                        month: Int,
                                        day: Int)

case class CiriumScheduledResponseError(cause: String) extends NoStackTrace