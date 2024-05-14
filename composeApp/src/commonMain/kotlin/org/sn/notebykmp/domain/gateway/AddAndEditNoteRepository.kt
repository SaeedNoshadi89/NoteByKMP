package org.sn.notebykmp.domain.gateway

import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface AddAndEditNoteRepository {

    suspend fun createNote(
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int,
    ): String

    suspend fun getNoteById(noteId: String): Flow<Note?>

    suspend fun updateNote(
        noteId: String,
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int,
    )

    fun getCategories(): Flow<List<Category>>}