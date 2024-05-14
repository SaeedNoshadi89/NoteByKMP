package org.sn.notebykmp.domain.usecase

import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository

class AddNoteUseCase (private val repository: AddAndEditNoteRepository) {
    suspend operator fun invoke(
        title: String,
        description: String,
        dueDateTime: String,
        isCompleted: Boolean,
        category: Int
    ) =
        repository.createNote(
            title = title,
            description = description,
            dueDateTime = dueDateTime,
            isCompleted = isCompleted,
            category = category
        )
}