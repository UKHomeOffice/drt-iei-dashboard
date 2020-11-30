package uk.gov.homeoffice.utils

import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Date

object DateUtil {

  val parseDate: String => Date = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)

  val formatDate: Date => String = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)

  val formatRequestDate: Date => String = date => new SimpleDateFormat("yyyy-MM-dd").format(date)

  val parseRequestDate: String => Date = date => new SimpleDateFormat("yyyy-MM-dd").parse(date)

  val isSameDate: (Date, Date) => Boolean = (date1, date2) =>
    date1.toInstant().atZone(ZoneOffset.UTC).toLocalDate.
      compareTo(date2.toInstant().atZone(ZoneOffset.UTC).toLocalDate) == 0
}
