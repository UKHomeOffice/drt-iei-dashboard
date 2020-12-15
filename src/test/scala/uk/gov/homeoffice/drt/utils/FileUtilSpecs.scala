package uk.gov.homeoffice.drt.utils

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.drt.drt.utils.FileUtil

class FileUtilSpecs extends AsyncFlatSpec with Matchers {

  "File" should "read file and content" in {
    FileUtil.readResourceFile("test.html") mustEqual "This is test file"
  }


}
