package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.model.CalendarUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.gateway.NotesRepository

class GetCalendarUseCase constructor(private val repository: NotesRepository) {
    operator fun invoke(
        startDate: LocalDate,
        lastSelectedDate: LocalDate,
    ): Flow<CalendarUiModel> = repository.getCalendar(startDate, lastSelectedDate)

}