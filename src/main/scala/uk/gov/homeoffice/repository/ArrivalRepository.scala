package uk.gov.homeoffice.repository

import uk.gov.homeoffice.Util
import uk.gov.homeoffice.model.Arrival

class ArrivalRepository {

  //    code   | number | destination | origin | terminal | gate | stand |    status    |      scheduled      | estimated | actual | estimatedchox | actualchox |         pcp         | totalpassengers | pcppassengers
  //  EZY6062  |   6062 | BRS         | BRS    | T1       |      |       | ACL Forecast | 2018-11-23 21:35:00 |           |        |               |            | 2018-11-23 21:56:00 |             149 |           149
  //  EZY6062  |   6062 | BRS         | ATH    | T1       |      |       | ACL Forecast | 2018-12-21 21:35:00 |           |        |               |            | 2018-12-21 21:56:00 |             149 |           149

  val arrivalMap =
    List(
      Arrival(
        scheduledArrivalDate = Util.parseDate("2018-11-23 21:35:00"),
        carrierName = "EZY",
        flightNumber = "6062",
        arrivingAirport = "BRS",
        origin = "BRS",
        scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        scheduledArrivalDate = Util.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZY",
        flightNumber = "6062",
        arrivingAirport = "BRS",
        origin = "ATH",
        scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        scheduledArrivalDate = Util.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZY",
        flightNumber = "6062",
        arrivingAirport = "BRS",
        origin = "CLJ",
        scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        scheduledArrivalDate = Util.parseDate("2019-12-21 21:35:0"),
        carrierName = "EZY",
        flightNumber = "6062",
        arrivingAirport = "BRS",
        origin = "CLJ",
        scheduledDepartureTime = Util.parseDate("2018-11-23 21:35:00")
      )

    )

}
