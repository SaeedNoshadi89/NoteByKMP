package org.sn.notebykmp.domain.gateway

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note

interface NotesRepository {

    fun getAllNotes(categoryId: Int?): Flow<List<Note>>

    fun getNoteById(noteId: String): Flow<Note?>

    suspend fun updateNote(
        noteId: String,
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
    )

    suspend fun deleteNote(noteId: String)

    suspend fun completeNote(noteId: String, isComplete: Boolean)
    suspend fun bookmarkNote(noteId: String, isBookmark: Boolean)

    suspend fun clearCompletedNotes()

    fun getCalendar(startDate: LocalDate, lastSelectedDate: LocalDate): Flow<CalendarUiModel>

    fun setDateToCalendar(date: LocalDate): Flow<CalendarUiModel>

    fun getCategories(): Flow<List<Category>>
}