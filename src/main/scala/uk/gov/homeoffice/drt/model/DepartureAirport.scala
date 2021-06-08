package uk.gov.homeoffice.drt.model

import uk.gov.homeoffice.drt.utils.AirportUtil

case class Port(code: String, name: String)

object DepartureAirport {

  def beneluxDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Netherlands", "Belgium", "Luxembourg").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def warsawDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Poland", "Czech Republic", "Ukraine", "Belarus", "Latvia", "Estonia", "Lithuania", "Iceland").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def berlinDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Germany", "Austria", "Switzerland", "Liechenstein", "Finland", "Denmark", "Norway", "Sweden").flatMap(AirportUtil.getPortListForCountry(_))
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

  def madridDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Spain", "Portugual").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def albaniaDeparturePortForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Albania", "Serbia", "North Macedonia", "Bosnia", "Kosovo", "Montenegro").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def athensDeparturePortsForCountry(implicit country: String): List[Port] = {
    country.toLowerCase match {
      case "all" => List("Greece", "Cyprus", "Croatia", "Slovenia", "Bulgaria", "Romania", "Moldova").flatMap(AirportUtil.getPortListForCountry(_))
      case _ => AirportUtil.getPortListForCountry
    }
  }

  def getDeparturePortForCountry(region: String, post: String)(implicit country: String): List[Port] = {
    (region.toLowerCase, post.toLowerCase) match {
      case (_, "benelux") => beneluxDeparturePortForCountry
      case (_, "warsaw") => warsawDeparturePortForCountry
      case (_, "berlin") => berlinDeparturePortForCountry
      case (_, "paris") => parisDeparturePortForCountry
      case (_, "rome") => romeDeparturePortForCountry
      case (_, "athens") => athensDeparturePortsForCountry
      case (_, "madrid") => madridDeparturePortForCountry
      case (_, "albania") => albaniaDeparturePortForCountry
      case ("euromed north", "all") => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry ::: parisDeparturePortForCountry
      case ("euromed south", "all") => romeDeparturePortForCountry ::: athensDeparturePortsForCountry ::: madridDeparturePortForCountry ::: albaniaDeparturePortForCountry
      case ("all", "all") => beneluxDeparturePortForCountry ::: warsawDeparturePortForCountry ::: berlinDeparturePortForCountry :::
        parisDeparturePortForCountry ::: romeDeparturePortForCountry ::: athensDeparturePortsForCountry :::
        madridDeparturePortForCountry ::: albaniaDeparturePortForCountry
      case _ => List.empty

    }
  }

}
