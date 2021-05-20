package uk.gov.homeoffice.drt.model

import uk.gov.homeoffice.drt.utils.AirportUtil

case class Port(code: String, name: String)

sealed trait Country {
  val defaultTimeZone = "UTC"

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

case object Poland extends Country {
  override val defaultTimeZone = "Europe/Warsaw"
  val portList = List(
    Port("EPBC", "Babice"),
    Port("GDN", "Lech Walesa"),
    Port("KRK", "Balice"),
    Port("EPKM", "Muchowiec"),
    Port("KTW", "Pyrzowice"),
    Port("EPML", "Mielec"),
    Port("POZ", "Lawica"),
    Port("RZE", "Jasionka"),
    Port("SZZ", "Goleniow"),
    Port("OSP", "Redzikowo"),
    Port("EPSN", "Swidwin"),
    Port("WAW", "Okecie"),
    Port("WRO", "Strachowice"),
    Port("IEG", "Babimost")

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

  def beneluxDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "netherlands" => Netherlands.portList
      case "belgium" => Belgium.portList
      case "luxembourg" => Luxembourg.portList
      case _ => List.empty
    }
  }

  def warsawDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Poland","Czech Republic","Ukraine","Belarus","Latvia","Estonia","Lithuania","Iceland").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def berlinDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Germany","Austria","Switzerland","Liechenstein","Finland","Denmark","Norway","Sweden").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def parisDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("France","Tunisia","Morocco","Algeria","Basel Mulhouse").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def romeDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Italy","Malta").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def madridDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Spain","Portugual").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def albaniaDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Albania","Serbia","North Macedonia","Bosnia","Kosovo","Montenegro").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def athensDeparturePortsForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "greece" => Greece.portList
      case "cyprus" => Cyprus.portList
      case "croatia" => Croatia.portList
      case "slovenia" => Slovenia.portList
      case "bulgaria" => Bulgaria.portList
      case "romania" => Romania.portList
      case "moldova" => Moldova.portList
      case "all" => Greece.portList ::: Cyprus.portList ::: Croatia.portList :::
        Slovenia.portList ::: Bulgaria.portList ::: Romania.portList ::: Moldova.portList
      case _ => List.empty
    }

  }

  def beneluxRegionsPortList = List(
    Netherlands, Belgium, Luxembourg
  )

  def athenRegionsPortList = List(
    Greece, Cyprus, Croatia, Slovenia, Bulgaria, Romania, Moldova
  )

  def getDeparturePortForCountry(post: String)(implicit country: String): List[Port] = {
    post.toLowerCase match {
      case "benelux" => beneluxDeparturePortForCountry
      case "warsaw" => warsawDeparturePortForCountry
      case "berlin" => berlinDeparturePortForCountry
      case "paris" => parisDeparturePortForCountry
      case "rome" => romeDeparturePortForCountry
      case "athens" => athensDeparturePortsForCountry
      case "madrid" => madridDeparturePortForCountry
      case "albania" => albaniaDeparturePortForCountry
      case "all" => if (country == "all") beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry :::
        parisDeparturePortForCountry ::: romeDeparturePortForCountry ::: athensDeparturePortsForCountry :::
        madridDeparturePortForCountry ::: albaniaDeparturePortForCountry
      else List.empty
      case _ => List.empty

    }
  }

}
