package uk.gov.homeoffice.drt.model

object DepartAirportTestModel {
  sealed trait Country {
    def portList: Seq[Port]
  }

  case object Greece extends Country {
    val portList = List(
      Port("PKH", "Alexion"),
      Port("PYR", "Andravida"),
      Port("AGQ", "Agrinion"),
      Port("AXD", "Dimokritos"),
      Port("LGAX", "Alexandria"),
      Port("VOL", "Nea Anchialos"),
      Port("LGEL", "Elefsis"),
      Port("JKH", "Chios"),
      Port("IOA", "Ioannina"),
      Port("HER", "Nikos Kazantzakis"),
      Port("KSO", "Aristotelis"),
      Port("KIT", "Kithira"),
      Port("EFL", "Kefallinia"),
      Port("KLX", "Kalamata"),
      Port("LGKM", "Amigdhaleon"),
      Port("KGS", "Kos"),
      Port("AOK", "Karpathos"),
      Port("CFU", "Ioannis Kapodistrias Intl"),
      Port("KSJ", "Kasos"),
      Port("KVA", "Megas Alexandros Intl"),
      Port("KZI", "Filippos"),
      Port("LRS", "Leros"),
      Port("LXS", "Limnos"),
      Port("LRA", "Larisa"),
      Port("LGMG", "Megara"),
      Port("JMK", "Mikonos"),
      Port("MJT", "Mitilini"),
      Port("PVK", "Aktio"),
      Port("LGRD", "Maritsa"),
      Port("RHO", "Rhodes Diagoras"),
      Port("GPA", "Araxos"),
      Port("CHQ", "Souda"),
      Port("JSI", "Alexandros Papadiamantis"),
      Port("SMI", "Samos"),
      Port("LGSP", "Sparti"),
      Port("JTR", "Santorini"),
      Port("JSH", "Sitia"),
      Port("LGSV", "Stefanovikion"),
      Port("SKU", "Skiros"),
      Port("LGTG", "Tanagra"),
      Port("LGTL", "Kasteli"),
      Port("LGTP", "Tripolis"),
      Port("SKG", "Makedonia"),
      Port("LGTT", "Tatoi"),
      Port("ZTH", "Dionysios Solomos"),
      Port("ATH", "Eleftherios Venizelos Intl"),
      Port("LGAT", "Ellinikon International Airport"),
      Port("JTY", "Astypalaia"),
      Port("JIK", "Ikaria"),
      Port("JKL", "Kalymnos Island"),
      Port("MLO", "Milos"),
      Port("JNX", "Naxos"),
      Port("PAS", "Paros"),
      Port("KZS", "Kastelorizo"),
      Port("HEW", "Athen Helenikon Airport"),
      Port("JSY", "Syros Airport"))
  }

  case object Cyprus extends Country {
    val portList = List(
      Port("LCA", "Larnaca"),
      Port("PFO", "Pafos Intl")
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
      Port("BOJ", "Burgas"),
      Port("PDV", "Plovdiv"),
      Port("SOF", "Sofia"))
  }

  case object Romania extends Country {
    val portList = List(
      Port("ARW", "Arad"),
      Port("BCM", "Bacau"),
      Port("BAY", "Tautii Magheraus"),
      Port("BBU", "Aurel Vlaicu"),
      Port("CND", "Mihail Kogalniceanu"),
      Port("CLJ", "Cluj Napoca"),
      Port("CSB", "Caransebes"),
      Port("CRA", "Craiova"),
      Port("IAS", "Iasi"),
      Port("OMR", "Oradea"),
      Port("OTP", "Henri Coanda"),
      Port("SBZ", "Sibiu"),
      Port("SUJ", "Satu Mare"),
      Port("SCV", "Stefan Cel Mare"),
      Port("TCE", "Cataloi"),
      Port("TGM", "Transilvania Targu Mures"),
      Port("TSR", "Traian Vuia"),
      Port("LRMS", "Aeroclub Mures"),
      Port("SIB", "Aeroclub Sibiu"),
      Port("CIO", "Aeroclub Cioca"),
      Port("LRBG", "Aeroclub Ghimbav"),
      Port("DVA", "Aeroclub Deva"),
      Port("DZM", "Aeroclub Cluj"),
      Port("LRTZ", "Tuzla"))
  }

  case object Moldova extends Country {
    val portList = List(Port("KIV", "Chisinau Intl"))
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
}
