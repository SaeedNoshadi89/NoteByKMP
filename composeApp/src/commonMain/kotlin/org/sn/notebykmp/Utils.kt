package org.sn.notebykmp

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


fun String?.formatTimeWithTwoNumber(): String {
    val parts = this?.split(":")
    val formattedHours = parts?.get(0)?.toInt().toString().padStart(2, '0')
    val formattedMinutes = parts?.get(1)?.toInt().toString().padStart(2, '0')
    return "$formattedHours:$formattedMinutes"
}

fun Int.withLeadingZero(): String {
    return if (this.toString().length == 1) {
        "0$this"
    } else {
        this.toString()
    }
}

fun LocalDate.generateDueDateTime(hour: Int, minute: Int) = LocalDateTime(
    this,
    LocalTime(
        hour,
        minute
    )
).toInstant(
    TimeZone.currentSystemDefault()
).toEpochMilliseconds().toString()

fun getDelayInMilliseconds(userSelectedDateTimeInMilliseconds: Long, todayDateTime: Instant): Long {
    val userSelectedDateTime = Instant.fromEpochMilliseconds(userSelectedDateTimeInMilliseconds)
    val timeDifference = userSelectedDateTime.minus(todayDateTime)
    return timeDifference.inWholeMilliseconds
}

fun String.getFormattedDateTime() =
    Instant.fromEpochMilliseconds(toLong())
        .toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

val monthAbbreviations = mapOf(
    1 to "Jan", 2 to "Feb", 3 to "Mar", 4 to "Apr",
    5 to "May", 6 to "Jun", 7 to "Jul", 8 to "Aug",
    9 to "Sep", 10 to "Oct", 11 to "Nov", 12 to "Dec"
)
val weekAbbreviations = mapOf(
    "SATURDAY" to "Saturday", "SUNDAY" to "Sunday", "MONDAY" to "Monday", "TUESDAY" to "Tuesday",
    "WEDNESDAY" to "Wednesday", "THURSDAY" to "Thursday", "FRIDAY" to "Friday"
)
fun <T> T.convertToUtc(): Instant? {
    return when (this) {
        is String -> Instant.parse(this).toLocalDateTime(TimeZone.UTC).toInstant(TimeZone.UTC)
        is Instant -> toLocalDateTime(TimeZone.UTC).toInstant(TimeZone.UTC)
        else -> null
    }
}
