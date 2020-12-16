package uk.gov.homeoffice.drt.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, ZoneId, ZoneOffset}
import java.util.Date

object DateUtil {

  val parseDate: String => Date = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)

  val formatDate: Date => String = date => new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)

  val formatRequestDate: Date => String = date => new SimpleDateFormat("yyyy-MM-dd").format(date)

  val parseRequestDate: String => Date = date => new SimpleDateFormat("yyyy-MM-dd").parse(date)

  val isSameDate: (Date, Date) => Boolean = (date1, date2) =>
    date1.toInstant().atZone(ZoneOffset.UTC).toLocalDate.
      compareTo(date2.toInstant().atZone(ZoneOffset.UTC).toLocalDate) == 0


  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  val formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  val parseLocalDateTime: String => LocalDateTime = dateString => LocalDateTime.parse(dateString, formatterWithTime)

  val formatLocalDateTime: LocalDateTime => String = date => formatterWithTime.format(date)

  val parseLocalDate: String => LocalDate = dateString => LocalDate.parse(dateString, formatter);

  val formatLocalDate: LocalDate => String = date => formatter.format(date)

  val dateDefaultTimeZoneConvert: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant())

  val dateAthensTimeZoneConvert: LocalDateTime => Date = localDate => Date.from(localDate.atZone(ZoneId.of("UTC+2")).toInstant())

}
