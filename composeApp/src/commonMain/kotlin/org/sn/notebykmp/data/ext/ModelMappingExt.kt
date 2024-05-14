package org.sn.notebykmp.data.ext

import org.sn.notebykmp.data.entity.NoteEntity
import org.sn.notebykmp.domain.model.Note
import kotlin.jvm.JvmName


fun Note.toEntity() = NoteEntity(
    id = id ?: "0",
    title = title,
    description = description,
    isCompleted = isCompleted,
    bookmark = isBookmark,
    dueDateTime = dueDateTime,
    category = category
)

fun NoteEntity.toModel() = Note(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    isBookmark = bookmark,
    dueDateTime = dueDateTime,
    category = category
)

@JvmName("entityToModel")
fun List<NoteEntity>.toModel() = map(NoteEntity::toModel)