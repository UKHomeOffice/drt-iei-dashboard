package uk.gov.homeoffice.drt.utils

import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time._
import java.util.Date

object DateUtil {

  val `yyyy-MM-dd HH:mm:ss_parse_toDate`: String => DateTime = date => DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

  val `yyyy-MM-dd HH:mm:ss_parse_toDate_withTimezone`: (String, String) => DateTime = (date, timeZone) => `yyyy-MM-dd HH:mm:ss_parse_toDate`(date).toDateTime(DateTimeZone.forID(timeZone));

  val `yyyy-MM-dd HH:mm_format_toString`: DateTime => String = date => DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").print(date);

  val `yyyy-MM-dd_format_toString`: Date => String = date => new SimpleDateFormat("yyyy-MM-dd").format(date)

  val `yyyy-MM-dd_parse_toDate`: String => DateTime = date => DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd"));

  val `yyyy-MM-ddTHH:mm:ss.SSSZ_parse_toLocalDateTime`: String => LocalDateTime = dateString => LocalDateTime.parse(dateString, `yyyy-MM-ddTHH:mm:ss.SSSZ_LocalDateFormatter`)

  val `yyyy-MM-ddTHH:mm:ss.SSSZ_LocalDateFormatter` = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

  val `yyyy-MM-dd_LocalDateFormatter` = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  val `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime` = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  val `yyyy-MM-dd HH:mm:ss_parse_toLocalDateTime`: String => LocalDateTime = dateString => LocalDateTime.parse(dateString, `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime`)

  val `yyyy-MM-dd HH:mm:ss_formatLocalDateTime_toString`: LocalDateTime => String = date => `yyyy-MM-dd HH:mm:ss_LocalDateFormatterWithTime`.format(date)

  val `yyyy-MM-dd_parse_toLocalDate`: String => LocalDate = dateString => LocalDate.parse(dateString, `yyyy-MM-dd_LocalDateFormatter`);

  val `yyyy-MM-dd_formatLocalDate_toString`: LocalDate => String = date => `yyyy-MM-dd_LocalDateFormatter`.format(date)

}
