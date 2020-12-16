package uk.gov.homeoffice.drt.utils

import scala.io.Source

object FileUtil {

  def readResourceFile(path: String) = {
    Source.fromResource(path).mkString
  }

}
