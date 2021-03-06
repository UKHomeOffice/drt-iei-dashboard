package uk.gov.homeoffice.drt.utils

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers

import java.time.Month

class DateUtilSpecs extends AsyncFlatSpec with Matchers {

  "Compare date check" should "be true for same dates and different time" in {
    val date1 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00").withTimeAtStartOfDay()
    val date2 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 22:35:00").withTimeAtStartOfDay()
    date1 mustEqual date2
  }

  "Compare date check" should "be true for same dates and one no time" in {
    val date1 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00").withTimeAtStartOfDay()
    val date2 = DateUtil.`yyyy-MM-dd_parse_toDate`("2018-11-23").withTimeAtStartOfDay()

    date1 mustEqual date2

  }

  "Compare date check" should "be false for different dates" in {
    val date1 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00").withTimeAtStartOfDay()
    val date2 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-24 22:35:00").withTimeAtStartOfDay()
    date1 must not be date2

  }

  "Date format" should "give string of `yyyy-MM-dd HH:mm:ss` format" in {
    val date1 = DateUtil.`yyyy-MM-dd HH:mm:ss_parse_toDate`("2018-11-23 21:35:00")

    val dateString = DateUtil.`yyyy-MM-dd HH:mm_format_toString`(date1)
    dateString mustEqual "2018-11-23 21:35"
  }

  "LocalDate format" should "give string of `yyyy-MM-dd` format" in {
    val date1 = DateUtil.`yyyy-MM-dd_parse_toLocalDate`("2018-11-23")

    val dateString = DateUtil.`yyyy-MM-dd_formatLocalDate_toString`(date1)
    dateString mustEqual "2018-11-23"
  }

  "Cirium Date format" should "parse string to Date" in {
    val date = DateUtil.`yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`("2019-07-15T09:10:00.000")
    date.getYear mustEqual 2019
    date.getMonth mustEqual Month.JULY
    date.getDayOfMonth mustEqual 15
    date.getHour mustEqual 9
    date.getMinute mustEqual 10
    date.getSecond mustEqual 0
  }
}
