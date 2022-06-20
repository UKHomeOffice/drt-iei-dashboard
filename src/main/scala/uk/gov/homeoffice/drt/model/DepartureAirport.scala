package uk.gov.homeoffice.drt.model

import uk.gov.homeoffice.drt.utils.AirportUtil

case class Port(code: String, name: String)

object DepartureAirport {

  def africaDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Cameroon", "Central African Republic", "Equatorial Guinea", "Nigeria").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def albaniaDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Albania", "Serbia", "Macedonia", "Bosnia and Herzegovina", "Kosovo", "Montenegro").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def athensDeparturePortsForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Greece", "Cyprus", "Croatia", "Slovenia", "Bulgaria", "Romania", "Moldova").flatMap(AirportUtil.getPortListForCountry(_))
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

  def guangdongProvinceDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("China", "Hong Kong", "Taiwan", "Macau", "Mongolia", "Philippines", "Japan", "North Korea", "Brunei", "South Korea", "Australia", "New Zealand", "Fiji").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def istanbulDeparturePortForCountry(implicit country: String) = {
    country.toLowerCase match {
      case "all" => List("Armenia", "Georgia", "Kazakhstan", "Turkey", "Turkmenistan", "Uzbekistan", "Azerbaijan", "Iraq", "Jordan", "Lebanon", "Syria").flatMap(AirportUtil.getPortListForCountry(_))
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
      case "all" => List("India", "Afghanistan", "Bhutan", "Burma", "Cambodia", "Laos", "Thailand", "Myanmar", "Vietnam").flatMap(AirportUtil.getPortListForCountry(_))
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
      case "all" => List("Italy", "Malta").flatMap(AirportUtil.getPortListForCountry(_))
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
      case (_, "africa") => africaDeparturePortForCountry
      case (_, "albania") => albaniaDeparturePortForCountry
      case (_, "athens") => athensDeparturePortsForCountry
      case (_, "benelux") => beneluxDeparturePortForCountry
      case (_, "berlin") => berlinDeparturePortForCountry
      case (_, "doha") => dohaDeparturePortForCountry
      case (_, "dubai") => dubaiDeparturePortForCountry
      case (_, "dublin") => dublinDeparturePortsForCountry
      case (_, "guangdong province") => guangdongProvinceDeparturePortForCountry
      case (_, "istanbul") => istanbulDeparturePortForCountry
      case (_, "madrid") => madridDeparturePortForCountry
      case (_, "new delhi") => newDelhiDeparturePortForCountry
      case (_, "paris") => parisDeparturePortForCountry
      case (_, "rome") => romeDeparturePortForCountry
      case (_, "warsaw") => warsawDeparturePortForCountry
      case ("africa", "all") => africaDeparturePortForCountry
      case ("china", "all") => guangdongProvinceDeparturePortForCountry
      case ("doha", "all") => dohaDeparturePortForCountry
      case ("euromed north", "all") => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry ::: parisDeparturePortForCountry
      case ("euromed south", "all") => romeDeparturePortForCountry ::: athensDeparturePortsForCountry ::: madridDeparturePortForCountry ::: albaniaDeparturePortForCountry
      case ("dubai", "all") => dubaiDeparturePortForCountry
      case ("india", "all") => newDelhiDeparturePortForCountry
      case ("istanbul", "all") => istanbulDeparturePortForCountry
      case ("all", "all") => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry :::
        parisDeparturePortForCountry ::: romeDeparturePortForCountry ::: athensDeparturePortsForCountry :::
        madridDeparturePortForCountry ::: guangdongProvinceDeparturePortForCountry ::: newDelhiDeparturePortForCountry ::: dubaiDeparturePortForCountry :::
        istanbulDeparturePortForCountry ::: dohaDeparturePortForCountry ::: africaDeparturePortForCountry

      case _ => List.empty

    }
  }

}
