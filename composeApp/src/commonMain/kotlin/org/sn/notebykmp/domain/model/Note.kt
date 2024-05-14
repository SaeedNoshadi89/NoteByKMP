package org.sn.notebykmp.domain.model

data class Note(
    val id: String? = null,
    val title: String = "",
    val description: String? = null,
    val dueDateTime: String = "",
    val isCompleted: Boolean = false,
    val isBookmark: Boolean = false,
    val category: Int = 1
){
    val isActive
        get() = !isCompleted

}