package org.sn.notebykmp.presentation.screen.notes.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.archive_minus
import notebykmp.composeapp.generated.resources.edit
import notebykmp.composeapp.generated.resources.rectangle
import org.jetbrains.compose.resources.painterResource
import org.sn.notebykmp.domain.model.Note


@Composable
internal fun NoteRow(
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    note: Note,
    onEditNote: (noteId: String) -> Unit = {},
    onCompleteNote: (String?, Boolean) -> Unit = { noteId, completed -> },
    onDeleteNote: (String?) -> Unit = {},
    onBookmark: (String?, Boolean) -> Unit = { noteId, bookmarked -> },
) {
    var expanded by remember { mutableStateOf(false) }
    val textDecoration = if (!note.isActive) TextDecoration.LineThrough
    else TextDecoration.None
    val bookmarkColor =
        if (note.isBookmark) MaterialTheme.colorScheme.primary else Color.Unspecified

    Surface(
        modifier = modifier
            .height(179.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 4.dp
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    textDecoration = textDecoration
                )
                if (editable){
                    Column {
                        Icon(
                            modifier = Modifier.clickable { expanded = !expanded },
                            painter = painterResource(Res.drawable.rectangle),
                            contentDescription = "rectangle",
                            tint = Color.Unspecified
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete", fontWeight = FontWeight.Bold) },
                                onClick = { onDeleteNote(note.id) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Text(
                modifier = modifier.weight(1f),
                text = note.description ?: "There is no description",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelLarge,
                textDecoration = textDecoration
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                if (editable) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = note.isCompleted,
                            onCheckedChange = {
                                onCompleteNote(note.id, !note.isCompleted)
                            }
                        )
                        Icon(
                            modifier = Modifier.clickable { note.id?.let { onEditNote(it) } },
                            painter = painterResource(Res.drawable.edit),
                            contentDescription = "edit",
                            tint = Color.Unspecified
                        )
                    }
                }
                IconButton(onClick = {
                    onBookmark(note.id, !note.isBookmark)
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.archive_minus),
                        contentDescription = "save",
                        tint = bookmarkColor
                    )
                }
            }
        }
    }
}