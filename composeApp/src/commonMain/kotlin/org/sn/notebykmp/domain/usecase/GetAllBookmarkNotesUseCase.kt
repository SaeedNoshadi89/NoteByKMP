package org.sn.notebykmp.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sn.notebykmp.domain.gateway.BookmarkNotesRepository
import org.sn.notebykmp.domain.model.Note

class GetAllBookmarkNotesUseCase (private val repository: BookmarkNotesRepository) {
    operator fun invoke(): Flow<List<Note>> =
        repository.getAllBookmarkNotes()
}