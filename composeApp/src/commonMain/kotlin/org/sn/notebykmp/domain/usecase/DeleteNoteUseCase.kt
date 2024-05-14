package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.NotesRepository

class DeleteNoteUseCase constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(noteId: String) =
        repository.deleteNote(noteId)
}