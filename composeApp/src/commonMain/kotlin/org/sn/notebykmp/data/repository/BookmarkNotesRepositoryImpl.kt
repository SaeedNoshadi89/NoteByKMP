package org.sn.notebykmp.data.repository

import kotlinx.coroutines.flow.Flow
import org.sn.notebykmp.data.data_sourse.LocalDataSource
import org.sn.notebykmp.domain.gateway.BookmarkNotesRepository
import org.sn.notebykmp.domain.model.Note

class BookmarkNotesRepositoryImpl(
    private val localDataSource: LocalDataSource,
) : BookmarkNotesRepository {

    override fun getAllBookmarkNotes(): Flow<List<Note>> =
        localDataSource.getAllBookmarkNotes()

}