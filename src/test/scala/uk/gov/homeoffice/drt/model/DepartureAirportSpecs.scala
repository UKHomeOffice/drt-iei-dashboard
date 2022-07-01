package uk.gov.homeoffice.drt.model

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.model.DepartAirportTestModel._
class DepartureAirportSpecs extends AsyncFlatSpec with Matchers {

  "Departure Airport code" should "give list of all airport for Greece" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Greece")
    greeceList mustEqual Greece.portList
  }

  "Departure Airport code" should "give list of all airport for Cyprus" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Cyprus")
    greeceList must contain allElementsOf Cyprus.portList
  }

  "Departure Airport code" should "give list of all airport for Croatia" in {
    val greeceList = DepartureAirport.tiranaDeparturePortForCountry("Croatia")
    greeceList must contain allElementsOf Croatia.portList
  }

  "Departure Airport code" should "give list of all airport for Slovenia" in {
    val greeceList = DepartureAirport.romeDeparturePortForCountry("Slovenia")
    greeceList must contain allElementsOf Slovenia.portList
  }

  "Departure Airport code" should "give list of all airport for Bulgaria" in {
    val greeceList = DepartureAirport.bucharestDeparturePortForCountry("Bulgaria")
    greeceList must contain allElementsOf  Bulgaria.portList
  }

  "Departure Airport code" should "give list of all airport for Romania" in {
    val greeceList = DepartureAirport.bucharestDeparturePortForCountry("Romania")
    greeceList must contain allElementsOf  Romania.portList
  }

  "Departure Airport code" should "give list of all airport for Moldova" in {
    val greeceList = DepartureAirport.bucharestDeparturePortForCountry("Moldova")
    greeceList must contain allElementsOf  Moldova.portList
  }


  "Departure Airpot Code" should "give list of all airport in the Athen post" in {
    val athensList = DepartureAirport.getDeparturePortForCountry("euromed south","Athens")("All")
    athensList must contain allElementsOf Greece.portList ::: Cyprus.portList
  }

}







