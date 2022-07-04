package uk.gov.homeoffice.drt.model

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.model.DepartAirportTestModel._
class DepartureAirportSpecs extends AsyncFlatSpec with Matchers {

  "Departure Airport code" should "give list of all airport for Greece" in {
    val countryList = DepartureAirport.athensDeparturePortsForCountry("Greece")
    countryList mustEqual Greece.portList
  }

  "Departure Airport code" should "give list of all airport for Cyprus" in {
    val countryList = DepartureAirport.athensDeparturePortsForCountry("Cyprus")
    countryList must contain allElementsOf Cyprus.portList
  }

  "Departure Airport code" should "give list of all airport for Croatia" in {
    val countryList = DepartureAirport.tiranaDeparturePortForCountry("Croatia")
    countryList must contain allElementsOf Croatia.portList
  }

  "Departure Airport code" should "give list of all airport for Slovenia" in {
    val countryList = DepartureAirport.romeDeparturePortForCountry("Slovenia")
    countryList must contain allElementsOf Slovenia.portList
  }

  "Departure Airport code" should "give list of all airport for Bulgaria" in {
    val countryList = DepartureAirport.bucharestDeparturePortForCountry("Bulgaria")
    countryList must contain allElementsOf  Bulgaria.portList
  }

  "Departure Airport code" should "give list of all airport for Romania" in {
    val countryList = DepartureAirport.bucharestDeparturePortForCountry("Romania")
    countryList must contain allElementsOf  Romania.portList
  }

  "Departure Airport code" should "give list of all airport for Moldova" in {
    val countryList = DepartureAirport.bucharestDeparturePortForCountry("Moldova")
    countryList must contain allElementsOf  Moldova.portList
  }


  "Departure Airpot Code" should "give list of all airport in the Athen post" in {
    val athensList = DepartureAirport.getDeparturePortForCountry("euromed south","Athens")("All")
    athensList must contain allElementsOf Greece.portList ::: Cyprus.portList
  }

}







