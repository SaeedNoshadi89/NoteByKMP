package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.NotesRepository

class BookmarkNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(
        noteId: String,
        isBookmark: Boolean,
    ) = repository.bookmarkNote(
        noteId = noteId,
        isBookmark = isBookmark
    )
}