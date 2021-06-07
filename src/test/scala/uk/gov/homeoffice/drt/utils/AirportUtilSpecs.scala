package uk.gov.homeoffice.drt.utils

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.model.Airport
import uk.gov.homeoffice.drt.model.DepartAirportTestModel.Poland

class AirportUtilSpecs extends AsyncFlatSpec with Matchers  {

  val airportDetails = AirportUtil.airportDetails

  "AirportUtil" should "give airport 8107 airports" in {
    airportDetails.size mustEqual  8107
  }

  "AirportUtil" should "give detail of airport for port AEY as excepted" in {
    val aeyAirportDetails = airportDetails.filter(_.iata ==  "AEY").head
    aeyAirportDetails mustEqual Airport("Akureyri","Akureyri","Iceland","AEY","BIAR","Atlantic/Reykjavik")

  }

  "Poland port" should "match the list of port as expected" in {
    val polandAirport = AirportUtil.getPortListForCountry("Poland")
    polandAirport must contain allElementsOf Poland.portList
  }
}
