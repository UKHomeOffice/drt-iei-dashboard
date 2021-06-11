package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.syntax.all._
import org.joda.time.{DateTime, DateTimeZone}
import uk.gov.homeoffice.drt.model
import uk.gov.homeoffice.drt.model._
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryI, ArrivalTableData, DepartureRepositoryI, DepartureTableData}
import uk.gov.homeoffice.drt.utils.DateUtil._
import uk.gov.homeoffice.drt.utils.{AirlineUtil, AirportUtil}

import java.time.LocalDateTime

class FlightScheduledService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F], departureRepository: DepartureRepositoryI[F]) {

  def getFlightsDetail(requestedDetails: FlightsRequest): F[List[ArrivalTableDataIndex]] = {
    val requestedDate = `yyyy-MM-dd_parse_toLocalDate`(requestedDetails.date).atStartOfDay()
    val portList: List[Port] = DepartureAirport.getDeparturePortForCountry(requestedDetails.region, requestedDetails.post)(requestedDetails.country)
    val arrivalFlights: F[List[ArrivalTableData]] = arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(a => portList.map(_.code) contains a.origin))
    arrivalFlights.map(_.zipWithIndex.map(a => ArrivalTableDataIndex(a._1, a._2)))

  }

  def transformArrivalsFromArrivalTable(requestedDetails: FlightsRequest, arrivalsTableData: F[List[ArrivalTableDataIndex]]): F[List[Arrival]] = {
    arrivalsTableData.map(_.map(a =>
      model.Arrival((a.index + 1).toString,
        localDateTimeAccordingToTimezone(requestedDetails, a.arrivalsTableData.scheduled),
        carrierName(a.arrivalsTableData.code, a.arrivalsTableData.number.toString),
        a.arrivalsTableData.code.toString,
        a.arrivalsTableData.destination,
        a.arrivalsTableData.origin,
        getDisplayStatus(a.arrivalsTableData.status, a.arrivalsTableData.totalPaxNumber),
        a.arrivalsTableData.scheduled_departure.map(d => localDateTimeAccordingToTimezone(requestedDetails, d)))))
  }

  def getDisplayStatus(status: String, totalPaxNumber: Option[Int]): String = status match {
    case _ if totalPaxNumber.getOrElse(0) == 0  => "NoPaxInfo"
    case "ACL Forecast" | "Port Forecast" => "Forecast"
    case "CANCELLED" | "Cancelled" | "Canceled" => "Cancelled"
    case "Deleted / Removed Flight Record" => "Deleted"
    case "DIVERTED" | "Diverted" | "Arrival diverted away from airport" | "Arrival is on block at a stand" | "On Approach" |
         "First Bag Delivered" | "Last Bag Delivered" | "Active" | "LANDED" | "Arrived" | "ARRIVED ON STAND" |
         "InApproach" | "Landed" | "ON APPROACH" | "Delayed" | "Zoned" | "Zoning" | "Final Approach" |
         "EXPECTED" | "BAGGAGE IN HALL" | "Redirected" | "Airborne from preceding airport" |
         "Flight is on schedule" | "LAST BAG DELIVERED" | "On Chocks" | "Finals" | "On Finals" => "Active"
    case "Scheduled" | "Estimated" | "RESCHEDULED" | "Calculated" | "Operated" => "Scheduled"
    case _ => "Others"

  }

  def localDateTimeAccordingToTimezone(requestedDetails: FlightsRequest, localtime: LocalDateTime): DateTime = {
    val dateTime = new DateTime(localtime.getYear, localtime.getMonthValue, localtime.getDayOfMonth, localtime.getHour, localtime.getMinute, localtime.getSecond, DateTimeZone.UTC)
    (requestedDetails.country, requestedDetails.timezone.toLowerCase) match {
      case (_, "uk") => dateTime.toDateTime(DateTimeZone.forID("Europe/London"))
      case ("All", "local") => dateTime
      case (country, "local") => dateTime.toDateTime(DateTimeZone.forID(AirportUtil.getTimezoneForCountry(country)))
      case (_, _) => dateTime
    }

  }

  def carrierName(code: String, number: String): String = {
    val iataCode = code.stripSuffix(number).stripSuffix("0").stripSuffix("0")
    AirlineUtil.getCarrierNameByIData(iataCode).map(_.name).getOrElse(AirlineUtil.getCarrierNameByICAO(iataCode).map(_.name).getOrElse(""))
  }


  def getScheduledDeparture: F[List[ArrivalTableData]] = {
    arrivalsRepository.getArrivalForOriginsAndDate(allPortsCodes)
  }

  val allPortsCodes: List[String] = DepartureAirport.getDeparturePortForCountry("All", "All")("All").map(_.code)


  def insertDepartureTableData(arrivalTableDataF: F[List[ArrivalTableData]]) = {
    val insertDepartureTableDataF: F[List[DepartureTableData]] = arrivalTableDataF.flatMap(_.traverse(departureRepository.upsertScheduledDeparture(_))).map(_.flatten)
    insertDepartureTableDataF.map(departureRepository.insertDepartureData(_)).flatten
  }


  def updateScheduledDepartureForArrival(arrivalTableDataF: F[List[ArrivalTableData]]) = {
    arrivalTableDataF.flatMap(arrivalsRepository.updateDepartureDate(_))
  }
}