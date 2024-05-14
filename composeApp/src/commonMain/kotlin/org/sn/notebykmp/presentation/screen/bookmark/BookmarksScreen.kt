package org.sn.notebykmp.presentation.screen.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.you_have_not_any_notes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.sn.notebykmp.presentation.screen.notes.component.NoteRow

@Composable
fun BookmarksScreen(modifier: Modifier = Modifier) {
    val viewModel = koinInject<BookmarksViewModel>()
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.bookmarkNotes.isEmpty() -> Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(Res.string.you_have_not_any_notes),
                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
            )

            else -> {
                LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(state.bookmarkNotes, key = { it.id ?: "" }) { note ->
                        NoteRow(
                            modifier = modifier.fillMaxWidth(),
                            editable = false,
                            note = note,
                        )
                    }
                }


            }
        }
    }


}