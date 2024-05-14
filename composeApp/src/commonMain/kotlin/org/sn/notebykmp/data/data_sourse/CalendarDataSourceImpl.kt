package org.sn.notebykmp.data.data_sourse

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.sn.notebykmp.domain.model.CalendarUiModel

class CalendarDataSourceImpl: CalendarDataSource {

    override val today: LocalDate
        get() {
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        }

    override fun getData(startDate: LocalDate, lastSelectedDate: LocalDate): CalendarUiModel {
        val endDayOfMonth = startDate.plus(6, DateTimeUnit.MONTH)

        val visibleDates = getDatesBetween(startDate, endDayOfMonth)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    override fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = startDate.daysUntil(endDate)
        return (0 until numOfDays).map { startDate.plus(it, DateTimeUnit.DAY) }
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it == lastSelectedDate)
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date == today,
        date = date,
    )

}