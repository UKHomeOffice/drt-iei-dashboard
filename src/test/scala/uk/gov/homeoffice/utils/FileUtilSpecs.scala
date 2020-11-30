package uk.gov.homeoffice.utils

import org.specs2.mutable.Specification

class FileUtilSpecs extends Specification {


  "File" should {
    "read file and content" in {
      FileUtil.readResourceFile("test.html") must contain ("This is test file")
    }
  }

}
