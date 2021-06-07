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
    val expectedAirports = List(
      Port("LCA", "Larnaca"),
      Port("PFO", "Pafos Intl")
    )
    greeceList must contain allElementsOf Cyprus.portList
  }

  "Departure Airport code" should "give list of all airport for Croatia" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Croatia")
    greeceList must contain allElementsOf Croatia.portList
  }

  "Departure Airport code" should "give list of all airport for Slovenia" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Slovenia")
//    val expectedAirports = List(Port("LJU", "Ljubljana"))
    greeceList must contain allElementsOf Slovenia.portList
  }

  "Departure Airport code" should "give list of all airport for Bulgaria" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Bulgaria")
//    val expectedAirports = List(
//      Port("VAR", "Varna"),
//      Port("BOJ", "Bourgas"),
//      Port("PDV", "Plovdiv"),
//      Port("SOF", "Sofia"))
    greeceList must contain allElementsOf  Bulgaria.portList
  }

  "Departure Airport code" should "give list of all airport for Romania" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Romania")
//    val expectedAirports = List(
//      Port("OTP", "Otopeni"),
//      Port("BBU", "Baneasa"),
//      Port("CLJ", "Cluj-Napoca"),
//      Port("SBZ", "Sibiu"),
//      Port("TSR", "Timisoara"),
//      Port("BCM", "Bacău"),
//      Port("CND", "Constanta"),
//      Port("CRA", "Craiova")
//    )
    greeceList must contain allElementsOf  Romania.portList
  }

  "Departure Airport code" should "give list of all airport for Moldova" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Moldova")
//    val expectedAirports = List(Port("KIV", "Chisinau"))
    greeceList must contain allElementsOf  Moldova.portList
  }
}







