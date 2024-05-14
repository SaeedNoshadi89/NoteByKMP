package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.NotesRepository

class CompleteNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(
        noteId: String,
        isComplete: Boolean,
    ) = repository.completeNote(
        noteId = noteId,
        isComplete = isComplete
    )
}