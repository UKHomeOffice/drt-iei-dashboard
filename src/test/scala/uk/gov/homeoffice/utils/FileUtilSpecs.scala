package uk.gov.homeoffice.utils

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers

class FileUtilSpecs extends AsyncFlatSpec with Matchers {

  "File" should "read file and content" in {
    FileUtil.readResourceFile("test.html") mustEqual "This is test file"
  }


}
