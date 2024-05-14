package org.sn.notebykmp.data.data_sourse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.sn.notebykmp.data.database.AppDatabase
import org.sn.notebykmp.data.entity.NoteEntity
import org.sn.notebykmp.data.ext.toModel
import org.sn.notebykmp.domain.model.Note


class LocalDataSourceImpl(private val appDatabase: AppDatabase) : LocalDataSource {

    private val mutex = Mutex()
    override fun getAllNotes(categoryId: Int?): Flow<List<Note>> = flow {
        mutex.withLock {
           val data = if (categoryId == 1) appDatabase.noteDao().getAllNotes().toModel() else appDatabase.noteDao().getNotes(
                categoryId
            ).first().toModel()
            emit(data)
        }
    }

    override fun getAllBookmarkNotes(): Flow<List<Note>> = flow {
        mutex.withLock {
            val data = appDatabase.noteDao().getAllBookmarkNotes().toModel()
            emit(data)
        }
    }

    override suspend fun upsertNote(note: NoteEntity) = mutex.withLock {
        appDatabase.noteDao().upsertNote(note)
    }

    override suspend fun deleteNote(noteId: String): Int = mutex.withLock {
        appDatabase.noteDao().deleteById(noteId)
    }

    override suspend fun completeNote(noteId: String, isComplete: Boolean) = mutex.withLock {
        appDatabase.noteDao().updateCompleted(noteId = noteId, completed = isComplete)
    }

    override suspend fun bookmarkNote(noteId: String, isBookmark: Boolean)  = mutex.withLock {
        appDatabase.noteDao().updateBookmark(noteId = noteId, bookmark = isBookmark)
    }

    override suspend fun getNoteById(noteId: String): NoteEntity? = mutex.withLock {
        return appDatabase.noteDao().getNoteById(noteId = noteId).first()
    }

    override suspend fun clearCompletedNotes(): Int = mutex.withLock {
        appDatabase.noteDao().deleteCompleted()
    }

}
