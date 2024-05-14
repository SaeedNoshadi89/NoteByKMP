package org.sn.notebykmp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.data.data_sourse.CalendarDataSource
import org.sn.notebykmp.data.data_sourse.LocalData
import org.sn.notebykmp.data.data_sourse.LocalDataSource
import org.sn.notebykmp.data.ext.toModel
import org.sn.notebykmp.domain.gateway.NotesRepository
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note

class NotesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val calendarDataSource: CalendarDataSource
) : NotesRepository {

    override fun getAllNotes(categoryId: Int?): Flow<List<Note>> =
        localDataSource.getAllNotes(categoryId)

    override fun getNoteById(noteId: String): Flow<Note?> = flow {
        emit(localDataSource.getNoteById(noteId)?.toModel())
    }

    override suspend fun updateNote(
        noteId: String,
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
    ) {
        val note = localDataSource.getNoteById(noteId)?.copy(
            title = title,
            description = description,
            id = noteId,
            dueDateTime = dueDateTime,
            isCompleted = isCompleted,
        ) ?: throw Exception("Note (id $noteId) not found")

        localDataSource.upsertNote(note)
    }


    override suspend fun deleteNote(noteId: String) {
        localDataSource.deleteNote(noteId)
    }

    override suspend fun completeNote(noteId: String, isComplete: Boolean) {
        localDataSource.completeNote(noteId = noteId, isComplete = isComplete)
    }

    override suspend fun bookmarkNote(noteId: String, isBookmark: Boolean) {
        localDataSource.bookmarkNote(noteId = noteId, isBookmark = isBookmark)
    }

    override suspend fun clearCompletedNotes() {
        localDataSource.clearCompletedNotes()
    }

    override fun getCalendar(
        startDate: LocalDate,
        lastSelectedDate: LocalDate
    ): Flow<CalendarUiModel> = flowOf(
        calendarDataSource.getData(
            startDate = startDate,
            lastSelectedDate = lastSelectedDate
        )
    )

    override fun setDateToCalendar(date: LocalDate): Flow<CalendarUiModel> =
        flowOf(calendarDataSource.getData(lastSelectedDate = date))

    override fun getCategories(): Flow<List<Category>> = flowOf(LocalData.category)


}