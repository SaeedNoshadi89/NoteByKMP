package org.sn.notebykmp.presentation.screen.bookmark

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sn.notebykmp.domain.model.Note
import org.sn.notebykmp.domain.usecase.GetAllBookmarkNotesUseCase

class BookmarksViewModel(private val getAllBookmarkNotesUseCase: GetAllBookmarkNotesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(BookmarksUiState(isLoading = true))
    val uiState: StateFlow<BookmarksUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _uiState.value
    )

    init {
        getBookmarkNotes()
    }

    private fun getBookmarkNotes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            getAllBookmarkNotesUseCase()
                .collectLatest { notes ->
                    _uiState.update { it.copy(isLoading = false, bookmarkNotes = notes) }
                }
        }
    }
}

@Stable
data class BookmarksUiState(
    val bookmarkNotes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
