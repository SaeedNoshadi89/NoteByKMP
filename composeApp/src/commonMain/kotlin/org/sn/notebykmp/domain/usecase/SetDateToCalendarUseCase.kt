package org.sn.notebykmp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.gateway.NotesRepository
import org.sn.notebykmp.domain.model.CalendarUiModel

class SetDateToCalendarUseCase (private val repository: NotesRepository) {
    operator fun invoke(
        date: LocalDate,
    ): Flow<CalendarUiModel> = repository.setDateToCalendar(date)

}