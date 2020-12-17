package uk.gov.homeoffice.drt.model

case class DepartureAirport(country: Country, airportCode: String, airportName: String)

case class Port(code: String, name: String)

sealed trait Country

case object Greece extends Country {
  val portList = List(
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
}

case object Cyprus extends Country {
  val portList = List(
    Port("LCA", "Larnaca"),
    Port("PFO", "Paphos")
  )
}

case object Croatia extends Country {
  val portList = List(
    Port("ZAD", "Zadar"),
    Port("SPU", "Split"),
    Port("DBV", "Dubrovnik"),
    Port("ZAG", "Zagreb"),
    Port("PUY", "Pula"),
    Port("OSI", "Osijek"))
}


case object Slovenia extends Country {
  val portList = List(Port("LJU", "Ljubljana"))
}


case object Bulgaria extends Country {
  val portList = List(
    Port("VAR", "Varna"),
    Port("BOJ", "Bourgas"),
    Port("PDV", "Plovdiv"),
    Port("SOF", "Sofia"))
}

case object Romania extends Country {
  val portList = List(
    Port("OTP", "Otopeni"),
    Port("BBU", "Baneasa"),
    Port("CLJ", "Cluj-Napoca"),
    Port("SBZ", "Sibiu"),
    Port("TSR", "Timisoara"),
    Port("BCM", "Bacău"),
    Port("CND", "Constanta"),
    Port("CRA", "Craiova")
  )
}

case object Moldova extends Country {
  val portList = List(Port("KIV", "Chisinau"))
}

object DepartureAirport {
  def athensDeparturePortsForCountry(country: String): List[Port] = {
    country.toLowerCase match {
      case "greece" => Greece.portList
      case "cyprus" => Cyprus.portList
      case "croatia" => Croatia.portList
      case "slovenia" => Slovenia.portList
      case "bulgaria" => Bulgaria.portList
      case "romania" => Romania.portList
      case "moldova" => Moldova.portList
      case _ => List.empty
    }

  }


}
