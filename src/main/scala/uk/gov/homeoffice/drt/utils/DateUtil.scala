package uk.gov.homeoffice.drt.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, ZoneId, ZoneOffset}
import java.util.Date

object DateUtil {

  val `yyyy-MM-dd HH:mm:ss_parse_toDate`: String => Date = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)

  val `yyyy-MM-dd HH:mm:ss_format_toString`: Date => String = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)

  val `yyyy-MM-dd_format_toString`: Date => String = date => new SimpleDateFormat("yyyy-MM-dd").format(date)

  val `yyyy-MM-dd_parse_toDate`: String => Date = date => new SimpleDateFormat("yyyy-MM-dd").parse(date)

  val areEqual: (Date, Date) => Boolean = (date1, date2) =>
    date1.toInstant().atZone(ZoneOffset.UTC).toLocalDate.
      compareTo(date2.toInstant().atZone(ZoneOffset.UTC).toLocalDate) == 0


  val `yyyy-MM-dd_LocalDateFormatter` = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  val `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime` = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  val `yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`: String => LocalDateTime = dateString => LocalDateTime.parse(dateString, `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime`)

  val `yyyy-MM-dd HH:mm:ss_formatLocalDateTime_toString`: LocalDateTime => String = date => `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime`.format(date)

  val `yyyy-MM-dd_parse_toLocalDate`: String => LocalDate = dateString => LocalDate.parse(dateString, `yyyy-MM-dd_LocalDateFormatter`);

  val `yyyy-MM-dd_formatLocalDate_toString`: LocalDate => String = date => `yyyy-MM-dd_LocalDateFormatter`.format(date)

  val UTCTimeZoneConvertDate: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.of("UTC")).toInstant())

  val `UTC+2TimeZoneConvertDate`: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.of("UTC+2")).toInstant())

}
