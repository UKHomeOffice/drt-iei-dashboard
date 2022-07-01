package uk.gov.homeoffice.drt.model

import uk.gov.homeoffice.drt.utils.AirportUtil

case class Port(code: String, name: String)

object DepartureAirport {

  def lagosDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Cameroon", "Central African Republic", "Equatorial Guinea", "Nigeria").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def tiranaDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Albania", "Bosnia and Herzegovina", "Croatia", "Kosovo", "Macedonia", "Montenegro", "Serbia").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def athensDeparturePortsForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Greece", "Cyprus").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def bangkokDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Cambodia", "Myanmar", "Thailand").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def beneluxDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Netherlands", "Belgium", "Luxembourg").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def berlinDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Germany", "Austria", "Switzerland", "Liechenstein", "Finland", "Denmark", "Norway", "Sweden").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def dohaDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Qatar", "Bahrain", "Kuwait", "Saudi Arabia").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def dublinDeparturePortsForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Ireland").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def dubaiDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("United Arab Emirates", "Afghanistan", "Oman", "Yemen", "Iran").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def beijingDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("China", "Hong Kong", "Taiwan", "Macau", "Mongolia", "Philippines", "Japan", "North Korea", "Brunei", "South Korea", "Australia", "New Zealand", "Fiji").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def bucharestDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Bulgaria", "Romania", "Moldova").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def hanoiDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Laos", "Vietnam").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def istanbulDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Armenia", "Georgia", "Kazakhstan", "Turkey", "Turkmenistan", "Uzbekistan", "Azerbaijan", "Iraq", "Jordan", "Lebanon", "Syria").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def islamabadDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Pakistan").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def madridDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Spain", "Portugal").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def newDelhiDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("India", "Afghanistan", "Bhutan", "Burma").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def parisDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("France", "Tunisia", "Morocco", "Algeria", "Basel Mulhouse").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def romeDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Italy", "Malta", "Slovenia").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def warsawDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Poland", "Czech Republic", "Ukraine", "Belarus", "Latvia", "Estonia", "Lithuania", "Iceland").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }


  def getDeparturePortForCountry(region: String, post: String)(implicit country: String): List[Port] = {
    (region.toLowerCase, post.toLowerCase) match {
      case (_, "athens") => athensDeparturePortsForCountry
      case (_, "benelux") => beneluxDeparturePortForCountry
      case (_, "bangkok") => bangkokDeparturePortForCountry
      case (_, "berlin") => berlinDeparturePortForCountry
      case (_, "beijing") => beijingDeparturePortForCountry
      case (_, "bucharest") => bucharestDeparturePortForCountry
      case (_, "doha") => dohaDeparturePortForCountry
      case (_, "dubai") => dubaiDeparturePortForCountry
      case (_, "dublin") => dublinDeparturePortsForCountry
      case (_, "hanoi") => hanoiDeparturePortForCountry
      case (_, "istanbul") => istanbulDeparturePortForCountry
      case (_, "islamabad") => islamabadDeparturePortForCountry
      case (_, "madrid") => madridDeparturePortForCountry
      case (_, "new delhi") => newDelhiDeparturePortForCountry
      case (_, "paris") => parisDeparturePortForCountry
      case (_, "rome") => romeDeparturePortForCountry
      case (_, "warsaw") => warsawDeparturePortForCountry
      case ("asia pacific", _) => beijingDeparturePortForCountry ::: bangkokDeparturePortForCountry
      case ("africa", _) => lagosDeparturePortForCountry
      case ("euromed north", _) => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry ::: parisDeparturePortForCountry ::: bucharestDeparturePortForCountry
      case ("euromed south", _) => romeDeparturePortForCountry ::: athensDeparturePortsForCountry ::: madridDeparturePortForCountry
      case ("south and south east asia", _) => newDelhiDeparturePortForCountry ::: hanoiDeparturePortForCountry
      case ("middle east and pakistan", _) => dubaiDeparturePortForCountry ::: istanbulDeparturePortForCountry ::: dohaDeparturePortForCountry ::: islamabadDeparturePortForCountry
      case ("western balkans", _) => tiranaDeparturePortForCountry
      case ("all", _) => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry ::: bangkokDeparturePortForCountry ::: bucharestDeparturePortForCountry
        parisDeparturePortForCountry ::: romeDeparturePortForCountry ::: athensDeparturePortsForCountry ::: hanoiDeparturePortForCountry ::: dublinDeparturePortsForCountry
        madridDeparturePortForCountry ::: beijingDeparturePortForCountry ::: newDelhiDeparturePortForCountry ::: dubaiDeparturePortForCountry ::: istanbulDeparturePortForCountry :::
          dohaDeparturePortForCountry ::: lagosDeparturePortForCountry ::: tiranaDeparturePortForCountry ::: islamabadDeparturePortForCountry
      case _ => List.empty

    }
  }

}
