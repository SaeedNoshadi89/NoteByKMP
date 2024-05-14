package org.sn.notebykmp.data.data_sourse

import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.model.CalendarUiModel

interface CalendarDataSource {
    val today: LocalDate

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel

    fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate>
}