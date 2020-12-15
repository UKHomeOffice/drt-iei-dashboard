package uk.gov.homeoffice.drt.drt.model

case class DepartureAirport(country: String, airportCode: String, airportName: String)

object DepartureAirport {

  val athensCountryAirportList = List(
    DepartureAirport("Greece", "ATH", "Athens"),
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
    DepartureAirport("Greece", "SMI", "Samos"),
    DepartureAirport("Cyprus", "LCA", "Larnaca"),
    DepartureAirport("Cyprus", "PFO", "Paphos"),
    DepartureAirport("Croatia", "ZAD", "Zadar"),
    DepartureAirport("Croatia", "SPU", "Split"),
    DepartureAirport("Croatia", "DBV", "Dubrovnik"),
    DepartureAirport("Croatia", "ZAG", "Zagreb"),
    DepartureAirport("Croatia", "PUY", "Pula"),
    DepartureAirport("Croatia", "OSI", "Osijek"),
    DepartureAirport("Slovenia", "LJU", "Ljubljana"),
    DepartureAirport("Bulgaria", "VAR", "Varna"),
    DepartureAirport("Bulgaria", "BOJ", "Bourgas"),
    DepartureAirport("Bulgaria", "PDV", "Plovdiv"),
    DepartureAirport("Bulgaria", "SOF", "Sofia"),
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
    DepartureAirport("Romania", "SUJ", "Satu-Mare"),
    DepartureAirport("Moldova", "KIV", "Chisinau"))


  def getAthensCountryAirport(country: String) = {
    athensCountryAirportList.filter(_.country == country)
  }

}
