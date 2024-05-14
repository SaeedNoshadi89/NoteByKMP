package org.sn.notebykmp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.sn.notebykmp.data.data_sourse.LocalData
import org.sn.notebykmp.data.data_sourse.LocalDataSource
import org.sn.notebykmp.data.ext.toEntity
import org.sn.notebykmp.data.ext.toModel
import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note
import org.sn.notebykmp.randomUUID

class AddAndEditNoteRepositoryImpl (
    private val localDataSource: LocalDataSource,
) : AddAndEditNoteRepository {
    override suspend fun createNote(
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int,
    ): String {

        val noteId = withContext(Dispatchers.Default) {
            randomUUID()
        }
        val note = Note(
            title = title,
            description = description,
            id = noteId,
            dueDateTime = dueDateTime,
            isCompleted = isCompleted,
            category = category
        )

        localDataSource.upsertNote(note.toEntity())
        return noteId
    }

    override suspend fun getNoteById(noteId: String): Flow<Note?> = flow {
        emit(localDataSource.getNoteById(noteId)?.toModel())
    }

    override suspend fun updateNote(
        noteId: String,
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int,
    ) {
        val note = Note(
            title = title,
            description = description,
            id = noteId,
            dueDateTime = dueDateTime,
            isCompleted = isCompleted,
            category = category
        )

        localDataSource.upsertNote(note.toEntity())
    }

    override fun getCategories(): Flow<List<Category>> = flowOf(LocalData.category)

}