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
    val arrivalFlights: F[List[ArrivalTableData]] =
      if (requestedDetails.portList.nonEmpty) {
        arrivalsRepository.findArrivalsForADateAndFilterOrigins(requestedDetails.portList, requestedDate)
      } else {
        arrivalsRepository.findArrivalsForADateAndFilterOrigins(portList.map(_.code), requestedDate)
      }
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
        displayStatus(a.arrivalsTableData),
        a.arrivalsTableData.scheduled_departure.map(d => localDateTimeAccordingToTimezone(requestedDetails, d)))))
  }

  def displayStatusBasedOnStatusValue(status: String): String = status.toLowerCase match {
    case "acl forecast" | "port forecast" => "Forecast"
    case "cancelled" | "canceled" | "deleted / removed flight record" | "deleted" => "Cancelled"
    case "diverted" | "arrival diverted away from airport" | "arrival is on block at a stand" |
         "on approach" | "first bag delivered" | "last bag delivered" | "active" |
         "arrived" | "arrived on stand" | "inapproach" | "landed" | "delayed" | "zoned" |
         "zoning" | "final approach" | "expected" | "baggage in hall" | "redirected" |
         "airborne from preceding airport" | "flight is on schedule" | "on chocks" |
         "finals" | "on finals" => "Active"
    case "scheduled" | "estimated" | "rescheduled" | "calculated" | "operated" => "Scheduled"
    case _ => "Others"


  }

  def displayStatus(arrivalsTableData: ArrivalTableData) = {
    (arrivalsTableData.estimated, arrivalsTableData.actualChox, arrivalsTableData.actual, arrivalsTableData.estimatedChox, arrivalsTableData.status) match {
      case _ if arrivalsTableData.totalPaxNumber.getOrElse(0) == 0 => "No_Pax_Info"
      case (_, Some(_), _, _, _) => "Active"
      case (_, _, Some(_), _, _) => "Active"
      case (Some(_), _, _, _, _) => "Active"
      case (_, _, _, Some(_), s) if s.isEmpty => "Scheduled"
      case (_, _, _, _, s) => displayStatusBasedOnStatusValue(s)

    }
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
    arrivalsRepository.getArrivalForOriginsWithin3Days(allPortsCodes)
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