package org.sn.notebykmp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.sn.notebykmp.data.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE bookmark = true")
    suspend fun getAllBookmarkNotes(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE category = :categoryId")
    fun getNotes(categoryId: Int?): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: String): Flow<NoteEntity?>

    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Query("UPDATE note SET isCompleted = :completed WHERE id = :noteId")
    suspend fun updateCompleted(noteId: String, completed: Boolean)

    @Query("UPDATE note SET bookmark = :bookmark WHERE id = :noteId")
    suspend fun updateBookmark(noteId: String, bookmark: Boolean)

    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteById(noteId: String): Int

    @Query("DELETE FROM note WHERE isCompleted = 1")
    suspend fun deleteCompleted(): Int

}