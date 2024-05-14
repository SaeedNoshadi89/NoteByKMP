package org.sn.notebykmp.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.gateway.NotesRepository
import org.sn.notebykmp.domain.model.Note
import org.sn.notebykmp.getFormattedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllNotesUseCase (private val repository: NotesRepository) {
    operator fun invoke(categoryId: Int?, selectedDate: LocalDate?): Flow<List<Note>> =
        repository.getAllNotes(categoryId).flatMapConcat { notes ->
            val map = if (categoryId == 1) {
                notes
            } else {
                notes.filter { isMatchingDate(it.dueDateTime, selectedDate) }

            }
            flowOf(map)
        }

    private fun isMatchingDate(dueDateTime: String, selectedDate: LocalDate?): Boolean {
        if (selectedDate == null || dueDateTime.isEmpty()) return true

        val date = dueDateTime.getFormattedDateTime().date
        return date.toString() == selectedDate.toString()
    }

}