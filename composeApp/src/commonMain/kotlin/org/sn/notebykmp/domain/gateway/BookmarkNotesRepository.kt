package org.sn.notebykmp.domain.gateway

import kotlinx.coroutines.flow.Flow
import org.sn.notebykmp.domain.model.Note

interface BookmarkNotesRepository {
    fun getAllBookmarkNotes(): Flow<List<Note>>
}