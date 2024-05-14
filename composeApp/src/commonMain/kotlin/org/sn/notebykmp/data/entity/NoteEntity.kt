package org.sn.notebykmp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "note"
)
data class NoteEntity(
    @PrimaryKey val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("dueDateTime")
    val dueDateTime: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean = false,
    @SerialName("bookmark")
    val bookmark: Boolean = false,
    @SerialName("category")
    val category: Int
)