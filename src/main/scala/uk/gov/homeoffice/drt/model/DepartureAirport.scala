package uk.gov.homeoffice.drt.model

case class Port(code: String, name: String)

sealed trait Country {
  def portList: Seq[Port]
}

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

case object Netherlands extends Country {
  val portList = List(
    Port("AMS", "Schiphol"),
    Port("EHBD", "Budel"),
    Port("MST", "Maastricht"),
    Port("EHDL", "Deelen"),
    Port("EHDR", "Drachten"),
    Port("EIN", "Eindhoven"),
    Port("GRQ", "Eelde"),
    Port("EHGR", "Gilze Rijen"),
    Port("DHR", "De Kooy"),
    Port("EHLE", "Lelystad"),
    Port("LWR", "Leeuwarden"),
    Port("RTM", "Rotterdam"),
    Port("UTC", "Soesterberg"),
    Port("ENS", "Twenthe"),
    Port("LID", "Valkenburg"),
    Port("WOE", "Woensdrecht")
  )
}

case object Belgium extends Country {
  val portList = List(
    Port("ANR", "Deurne"),
    Port("EBBE", "Beauvechain"),
    Port("EBBL", "Kleine Brogel"),
    Port("BRU", "Brussels Natl"),
    Port("EBBX", "Bertrix"),
    Port("CRL", "Brussels South"),
    Port("EBCV", "Chievres Ab"),
    Port("EBFN", "Koksijde"),
    Port("EBFS", "Florennes"),
    Port("QKT", "Wevelgem"),
    Port("LGG", "Liege"),
    Port("OST", "Oostende"),
    Port("EBSL", "Zutendaal"),
    Port("EBST", "Sint Truiden"),
    Port("EBSU", "Saint Hubert Mil"),
    Port("EBUL", "Ursel"),
    Port("EBWE", "Weelde"),
    Port("EBZR", "Zoersel"),

  )
}

case object Luxembourg extends Country {
  val portList = List(
    Port("LUX", "Luxembourg")
  )
}


object DepartureAirport {

  def beneluxDeparturePortForCountry(country: String): List[Port] = {
    country.toLowerCase match {
      case "netherlands" => Netherlands.portList
      case "belgium" => Belgium.portList
      case "luxembourg" => Luxembourg.portList
      case _ => List.empty
    }
  }

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

  def beneluxRegionsPortList = List(
    Netherlands, Belgium, Luxembourg
  )

  def athenRegionsPortList = List(
    Greece, Cyprus, Croatia, Slovenia, Bulgaria, Romania, Moldova
  )

}
