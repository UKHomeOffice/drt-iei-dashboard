package uk.gov.homeoffice.repository

import uk.gov.homeoffice.model.Arrival
import uk.gov.homeoffice.utils.DateUtil

class ArrivalRepository {

  //    code   | number | destination | origin | terminal | gate | stand |    status    |      scheduled      | estimated | actual | estimatedchox | actualchox |         pcp         | totalpassengers | pcppassengers
  //  EZY6062  |   6062 | BRS         | BRS    | T1       |      |       | ACL Forecast | 2018-11-23 21:35:00 |           |        |               |            | 2018-11-23 21:56:00 |             149 |           149
  //  EZY6062  |   6062 | BRS         | ATH    | T1       |      |       | ACL Forecast | 2018-12-21 21:35:00 |           |        |               |            | 2018-12-21 21:56:00 |             149 |           149

  val arrivalMap =
    List(
      Arrival(
        _id = "1",
        scheduledArrivalDate = DateUtil.parseDate("2018-11-23 21:35:00"),
        carrierName = "EZY",
        flightNumber = "6061",
        arrivingAirport = "BRA",
        origin = "BRS",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "2",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZX",
        flightNumber = "6062",
        arrivingAirport = "BRB",
        origin = "ATH",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "3",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZZ",
        flightNumber = "6063",
        arrivingAirport = "BRC",
        origin = "CLJ",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "4",
        scheduledArrivalDate = DateUtil.parseDate("2019-12-21 21:35:0"),
        carrierName = "EZA",
        flightNumber = "6064",
        arrivingAirport = "BRD",
        origin = "CLJ",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "5",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZB",
        flightNumber = "6065",
        arrivingAirport = "BRE",
        origin = "CLJ",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "6",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZC",
        flightNumber = "6066",
        arrivingAirport = "BRF",
        origin = "CLJ",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "7",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZD",
        flightNumber = "6067",
        arrivingAirport = "BRG",
        origin = "CLJ",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      ),
      Arrival(
        _id = "8",
        scheduledArrivalDate = DateUtil.parseDate("2018-12-21 21:35:0"),
        carrierName = "EZD",
        flightNumber = "6067",
        arrivingAirport = "BRG",
        origin = "SOF",
        scheduledDepartureTime = DateUtil.parseDate("2018-11-23 21:35:00")
      )
    )

}
