package org.sn.notebykmp.presentation.screen.add_note.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun ConfirmAddDialog(
    openDialog: Boolean,
    modifier: Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            containerColor = MaterialTheme.colorScheme.background,
            text = {
                Text(
                    text = "Are you sure you want to create ot update a note?",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            dismissButton = {
                Text(
                    modifier = modifier.clickable { onDismiss() },
                    text = "Cancel",
                    color = Color.Red
                )
            },
            confirmButton = {
                Text(
                    modifier = modifier.clickable {
                        onConfirm()
                    },
                    text = "Yes",
                    color = MaterialTheme.colorScheme.primary
                )
            })
    }
}