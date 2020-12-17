package uk.gov.homeoffice.drt.model

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers

class DepartureAirportSpecs extends AsyncFlatSpec with Matchers {

  "Departure Airport code" should "give list of all airport for Greece" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Greece")
    val expectedAirports = List(
      Port("ATH", "Athens"),
      Port("SKG", "Thessaloniki"),
      Port("KLX", "Kalamata"),
      Port("KVA", "Kavala"),
      Port("VOL", "Volos"),
      Port("PVK", "Preveza"),
      Port("HER", "Heraklion"),
      Port("CHQ", "Chania"),
      Port("ZTH", "Zakynthos"),
      Port("RHO", "Rhodes"),
      Port("CFU", "Kerkyra"),
      Port("KGS", "Kos"),
      Port("JSI", "Skiathos"),
      Port("JMK", "Mykonos"),
      Port("JTR", "Santorini"),
      Port("EFL", "Kefalonia")
    )
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Cyprus" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Cyprus")
    val expectedAirports = List(
      Port("LCA", "Larnaca"),
      Port("PFO", "Paphos")
    )
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Croatia" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Croatia")
    val expectedAirports = List(
      Port("ZAD", "Zadar"),
      Port("SPU", "Split"),
      Port("DBV", "Dubrovnik"),
      Port("ZAG", "Zagreb"),
      Port("PUY", "Pula"),
      Port("OSI", "Osijek"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Slovenia" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Slovenia")
    val expectedAirports = List(Port("LJU", "Ljubljana"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Bulgaria" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Bulgaria")
    val expectedAirports = List(
      Port("VAR", "Varna"),
      Port("BOJ", "Bourgas"),
      Port("PDV", "Plovdiv"),
      Port("SOF", "Sofia"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Romania" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Romania")
    val expectedAirports = List(
      Port("OTP", "Otopeni"),
      Port("BBU", "Baneasa"),
      Port("CLJ", "Cluj-Napoca"),
      Port("SBZ", "Sibiu"),
      Port("TSR", "Timisoara"),
      Port("BCM", "Bacău"),
      Port("CND", "Constanta"),
      Port("CRA", "Craiova")
    )
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Moldova" in {
    val greeceList = DepartureAirport.athensDeparturePortsForCountry("Moldova")
    val expectedAirports = List(Port("KIV", "Chisinau"))
    greeceList mustEqual expectedAirports
  }
}







