package uk.gov.homeoffice.drt.model

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.drt.model.DepartureAirport

class DepartureAirportSpecs extends AsyncFlatSpec with Matchers {


  "Departure Airport code" should "give list of all airport for Greece" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Greece")
    val expectedAirports = Seq(DepartureAirport("Greece", "ATH", "Athens"),
      DepartureAirport("Greece", "SKG", "Thessaloniki"),
      DepartureAirport("Greece", "KLX", "Kalamata"),
      DepartureAirport("Greece", "KVA", "Kavala"),
      DepartureAirport("Greece", "VOL", "Volos"),
      DepartureAirport("Greece", "PVK", "Preveza"),
      DepartureAirport("Greece", "HER", "Heraklion"),
      DepartureAirport("Greece", "CHQ", "Chania"),
      DepartureAirport("Greece", "ZTH", "Zakynthos"),
      DepartureAirport("Greece", "RHO", "Rhodes"),
      DepartureAirport("Greece", "CFU", "Kerkyra"),
      DepartureAirport("Greece", "KGS", "Kos"),
      DepartureAirport("Greece", "JSI", "Skiathos"),
      DepartureAirport("Greece", "JMK", "Mykonos"),
      DepartureAirport("Greece", "JTR", "Santorini"),
      DepartureAirport("Greece", "EFL", "Kefalonia"),
      DepartureAirport("Greece", "LXS", "Limnos"),
      DepartureAirport("Greece", "MJT", "Mytilini"),
      DepartureAirport("Greece", "SMI", "Samos"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Cyprus" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Cyprus")
    val expectedAirports = Seq(DepartureAirport("Cyprus", "LCA", "Larnaca"),
      DepartureAirport("Cyprus", "PFO", "Paphos"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Croatia" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Croatia")
    val expectedAirports = Seq(DepartureAirport("Croatia", "ZAD", "Zadar"),
      DepartureAirport("Croatia", "SPU", "Split"),
      DepartureAirport("Croatia", "DBV", "Dubrovnik"),
      DepartureAirport("Croatia", "ZAG", "Zagreb"),
      DepartureAirport("Croatia", "PUY", "Pula"),
      DepartureAirport("Croatia", "OSI", "Osijek"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Slovenia" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Slovenia")
    val expectedAirports = Seq(DepartureAirport("Slovenia", "LJU", "Ljubljana"))
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Bulgaria" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Bulgaria")
    val expectedAirports = Seq(
      DepartureAirport("Bulgaria", "VAR", "Varna"),
      DepartureAirport("Bulgaria", "BOJ", "Bourgas"),
      DepartureAirport("Bulgaria", "PDV", "Plovdiv"),
      DepartureAirport("Bulgaria", "SOF", "Sofia")
    )
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Romania" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Romania")
    val expectedAirports = Seq(
      DepartureAirport("Romania", "OTP", "Otopeni"),
      DepartureAirport("Romania", "BBU", "Baneasa"),
      DepartureAirport("Romania", "CLJ", "Cluj-Napoca"),
      DepartureAirport("Romania", "SBZ", "Sibiu"),
      DepartureAirport("Romania", "TSR", "Timisoara"),
      DepartureAirport("Romania", "BCM", "Bacău"),
      DepartureAirport("Romania", "CND", "Constanta"),
      DepartureAirport("Romania", "CRA", "Craiova"),
      DepartureAirport("Romania", "IAS", "Iași"),
      DepartureAirport("Romania", "OMR", "Oradea"),
      DepartureAirport("Romania", "SCV", "Suceava"),
      DepartureAirport("Romania", "TGM", "Tirgu Mures"),
      DepartureAirport("Romania", "SUJ", "Satu-Mare")
    )
    greeceList mustEqual expectedAirports
  }

  "Departure Airport code" should "give list of all airport for Moldova" in {
    val greeceList = DepartureAirport.getAthensCountryAirport("Moldova")
    val expectedAirports = Seq(
      DepartureAirport("Moldova", "KIV", "Chisinau")

    )
    greeceList mustEqual expectedAirports
  }


}
