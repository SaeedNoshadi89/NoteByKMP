package org.sn.notebykmp.data.data_sourse

import kotlinx.coroutines.flow.Flow
import org.sn.notebykmp.data.entity.NoteEntity
import org.sn.notebykmp.domain.model.Note

interface LocalDataSource {
    fun getAllNotes(categoryId: Int?): Flow<List<Note>>

    fun getAllBookmarkNotes(): Flow<List<Note>>

    suspend fun upsertNote(note: NoteEntity)

    suspend fun deleteNote(noteId: String): Int

    suspend fun completeNote(noteId: String, isComplete: Boolean)
    suspend fun bookmarkNote(noteId: String, isBookmark: Boolean)

    suspend fun getNoteById(noteId: String): NoteEntity?

    suspend fun clearCompletedNotes(): Int
}