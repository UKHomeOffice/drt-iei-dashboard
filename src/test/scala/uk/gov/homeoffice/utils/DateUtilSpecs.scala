package uk.gov.homeoffice.utils

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.homeoffice.utils

class DateUtilSpecs extends AsyncFlatSpec with Matchers {

  import utils.DateUtil._


  "Compare date check" should "be true for same date and different time" in {
    val date1 = DateUtil.parseDate("2018-11-23 21:35:00")
    val date2 = DateUtil.parseDate("2018-11-23 22:35:00")

    isSameDate(date1, date2) mustEqual true
  }

  "Compare date check" should "be true for same date and one no time" in {
    val date1 = DateUtil.parseDate("2018-11-23 21:35:00")
    val date2 = DateUtil.parseRequestDate("2018-11-23")

    isSameDate(date1, date2) mustEqual true
  }


  "Compare date check" should "be false for different date" in {
    val date1 = DateUtil.parseDate("2018-11-23 21:35:00")
    val date2 = DateUtil.parseDate("2018-11-24 22:35:00")

    isSameDate(date1, date2) mustEqual false
  }


  "Date format" should "give string of exepected format" in {
    val date1 = DateUtil.parseDate("2018-11-23 21:35:00")

    val dateString = DateUtil.formatDate(date1)
    dateString mustEqual "2018-11-23 21:35:00"
  }

  "LocalDate format" should "give string of expected format" in {
    val date1 = DateUtil.parseLocalDate("2018-11-23")

    val dateString = DateUtil.formatLocalDate(date1)
    dateString mustEqual "2018-11-23"
  }

  "LocalDateTime format" should "give string of expected format" in {
    val date1 = DateUtil.parseLocalDateTime("2018-11-23")

//    val dateString = DateUtil.formatLocalDateTime(date1)
    date1 mustEqual "2018-11-23"
  }
}
