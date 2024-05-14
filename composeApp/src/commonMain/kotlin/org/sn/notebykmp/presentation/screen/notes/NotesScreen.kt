package org.sn.notebykmp.presentation.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.you_have_not_any_notes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.sn.notebykmp.presentation.screen.notes.component.CalendarUi
import org.sn.notebykmp.presentation.screen.notes.component.CategoryComponent
import org.sn.notebykmp.presentation.screen.notes.component.NoteRow
import org.sn.notebykmp.presentation.screen.notes.component.RecentAllNoteHeader

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    onEditNote: (noteId: String) -> Unit,
    onNavigateToBookmark: () -> Unit,
) {
    val viewModel = koinInject<NotesViewModel>()
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val lazyCalendarListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        viewModel.apply {
            getCategories()
            getNotes()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(38.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarUi(
            modifier = modifier,
            uiState = state,
            lazyCalendarListState = lazyCalendarListState,
            onTodayClick = {
                scope.launch {
                    lazyCalendarListState.animateScrollToItem(
                        0,
                    )
                    viewModel.selectDate()
                }
            },
            onSelectDateClick = {
                viewModel.selectDate(it.date)
            })
        CategoryComponent(
            modifier = modifier,
            uiState = state,
            onNavigateToBookmark = onNavigateToBookmark, onSelectCategory = {
                viewModel.selectCategory(it)
            })
        RecentAllNoteHeader(modifier)
        Box(modifier = modifier.weight(2f), contentAlignment = Alignment.Center) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.notes.isEmpty() -> Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(Res.string.you_have_not_any_notes),
                    style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                )

                else -> {
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(state.notes, key = { it.id ?: "0" }) { note ->
                            NoteRow(
                                note = note,
                                onEditNote = { onEditNote(it) },
                                onCompleteNote = { id, isComplete ->
                                    viewModel.completeNote(
                                        id,
                                        isComplete
                                    )
                                },
                                onDeleteNote = { viewModel.deleteNote(it) },
                                onBookmark = { id, isBookmark ->
                                    viewModel.bookmarkNote(
                                        noteId = id,
                                        isBookmark = isBookmark
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}