package org.sn.notebykmp.presentation.screen.notes

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.sn.notebykmp.convertToUtc
import org.sn.notebykmp.domain.gateway.NotesRepository
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note
import org.sn.notebykmp.domain.usecase.BookmarkNoteUseCase
import org.sn.notebykmp.domain.usecase.CompleteNoteUseCase
import org.sn.notebykmp.domain.usecase.DeleteNoteUseCase
import org.sn.notebykmp.domain.usecase.GetAllNotesUseCase
import org.sn.notebykmp.domain.usecase.GetCalendarUseCase
import org.sn.notebykmp.domain.usecase.SetDateToCalendarUseCase

class NotesViewModel (
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getCalendarUseCase: GetCalendarUseCase,
    private val setDateToCalendarUseCase: SetDateToCalendarUseCase,
    private val completeNoteUseCase: CompleteNoteUseCase,
    private val bookmarkNoteUseCase: BookmarkNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val repository: NotesRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState(isLoading = true))

    init {
        getCalendar()
        selectDate()
    }

    val uiState: StateFlow<NotesUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _uiState.value
    )

    fun getNotes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            getAllNotesUseCase(_uiState.value.selectedCategory?.id, _uiState.value.selectedDate)
                .collectLatest { notes ->
                    _uiState.update { it.copy(isLoading = false, notes = notes) }
            }
        }
    }

    private fun getCalendar(
        startDate: LocalDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date,
        lastSelectedDate: LocalDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    ) {
        getCalendarUseCase(startDate, lastSelectedDate).onEach { calendar ->
            _uiState.update { it.copy(calendar = calendar) }
        }.launchIn(viewModelScope)
    }

    fun selectDate(
        date: LocalDate? = Clock.System.now().convertToUtc()
            ?.toLocalDateTime(TimeZone.currentSystemDefault())?.date
    ) {
        date?.let { selectedDate ->
            _uiState.update { it.copy(selectedDate = selectedDate) }
            setDateToCalendarUseCase(selectedDate).onEach { calendar ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = true,
                        calendar = calendar,
                    )
                }
                getNotes()
            }.launchIn(viewModelScope)
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collectLatest { result ->
                _uiState.update { it.copy(categories = result, selectedCategory = result[0]) }
            }
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
        getNotes()
    }

    fun completeNote(noteId: String?, isComplete: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            noteId?.let {id ->
                completeNoteUseCase(
                    noteId = id,
                    isComplete = isComplete
                )
            }
            getNotes()
        }
    }

    fun deleteNote(noteId: String?){
        viewModelScope.launch(Dispatchers.IO) {
            noteId?.let {id ->
                deleteNoteUseCase(
                    noteId = id,
                )
            }
            getNotes()
        }
    }

    fun bookmarkNote(noteId: String?, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            noteId?.let {id ->
                bookmarkNoteUseCase(
                    noteId = id,
                    isBookmark = isBookmark
                )
            }
            getNotes()
        }
    }
}

@Stable
data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val calendar: CalendarUiModel? = null,
    val selectedDate: LocalDate? = null,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
