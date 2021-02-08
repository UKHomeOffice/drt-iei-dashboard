package uk.gov.homeoffice.drt.service

import cats.effect.Sync
import cats.kernel.Semigroup
import cats.syntax.all._
import org.slf4j.LoggerFactory
import uk.gov.homeoffice.drt.AppResource._
import uk.gov.homeoffice.drt.model
import uk.gov.homeoffice.drt.model._
import uk.gov.homeoffice.drt.repository.{ArrivalRepositoryI, ArrivalTableData, DepartureRepositoryI, DepartureTableData}
import uk.gov.homeoffice.drt.utils.DateUtil._

class ArrivalService[F[_] : Sync](arrivalsRepository: ArrivalRepositoryI[F], departureRepository: DepartureRepositoryI[F]) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def getFlightsDetail(requestedDetails: FlightsRequest): F[List[ArrivalTableDataIndex]] = {
    val requestedDate = `yyyy-MM-dd_parse_toLocalDate`(requestedDetails.date).atStartOfDay()
    val portList: List[Port] = DepartureAirport.athensDeparturePortsForCountry(requestedDetails.country)

    val arrivalFlights = arrivalsRepository.findArrivalsForADate(requestedDate).map(_.filter(a => portList.map(_.code) contains a.origin))
    val arrivalFlightsWithScheduledDeparture: F[List[ArrivalTableData]] = arrivalFlights.map(_.filterNot(_.scheduled_departure.isEmpty))
    val arrivalFlightsWithoutScheduledDeparture: F[List[ArrivalTableData]] = arrivalFlights.map(_.filter(_.scheduled_departure.isEmpty))

    val amendArrivalFlightsData: F[List[ArrivalTableData]] = arrivalFlightsWithoutScheduledDeparture.flatMap(_.traverse { arrivalTableData =>
      departureRepository.selectScheduleDepartureTableWithOutDeparture(arrivalTableData).map(_.headOption).map { d =>
        if (d.nonEmpty)
          arrivalTableData.copy(scheduled_departure = Option(d.head.scheduled_departure))
        else arrivalTableData
      }
    })

    val combined: F[List[ArrivalTableData]] = arrivalFlightsWithScheduledDeparture.map(a => amendArrivalFlightsData.map(b => Semigroup[List[ArrivalTableData]].combine(a, b))).flatten
    combined.map(_.zipWithIndex.map(a => ArrivalTableDataIndex(a._1, a._2)))

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

  def getScheduledDeparture: F[List[ArrivalTableData]] = {
    athenPortsCodes.traverse { originPort =>
      arrivalsRepository.getArrivalsForOriginAndDate(originPort)
    }.map(_.flatten)
  }

  val athenPortsCodes: List[String] = DepartureAirport.athenRegionsPortList.flatMap { country =>
    country.portList.map(_.code.toString)
  }


  def insertUpdateDepartureTableData(arrivalTableDataF: F[List[ArrivalTableData]]) = {
    val insertDepartureTableDataF: F[List[DepartureTableData]] = arrivalTableDataF.flatMap(_.traverse(departureRepository.ignoreScheduledDepartureIfExist(_))).map(_.flatten)
    insertDepartureTableDataF.map(departureRepository.insertDepartureData(_)).flatten
  }

}