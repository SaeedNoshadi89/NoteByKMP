package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository

class UpdateNoteUseCase constructor(private val repository: AddAndEditNoteRepository) {
    suspend operator fun invoke(
        noteId: String,
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int
    ) =
        repository.updateNote(
            noteId = noteId,
            title = title,
            description = description,
            dueDateTime = dueDateTime,
            isCompleted = isCompleted,
            category = category
        )
}