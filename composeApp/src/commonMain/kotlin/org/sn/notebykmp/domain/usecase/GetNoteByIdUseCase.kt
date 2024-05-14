package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository
import org.sn.notebykmp.domain.model.Note
import kotlinx.coroutines.flow.Flow

class GetNoteByIdUseCase constructor(private val repository: AddAndEditNoteRepository) {
    suspend operator fun invoke(noteId: String): Flow<Note?> =
        repository.getNoteById(noteId)

}