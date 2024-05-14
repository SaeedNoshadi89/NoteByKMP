package org.sn.notebykmp.presentation.screen.add_note

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.domain.model.Note
import org.sn.notebykmp.domain.model.TimeModel
import org.sn.notebykmp.domain.usecase.AddNoteUseCase
import org.sn.notebykmp.domain.usecase.GetNoteByIdUseCase
import org.sn.notebykmp.domain.usecase.UpdateNoteUseCase
import org.sn.notebykmp.generateDueDateTime
import org.sn.notebykmp.getFormattedDateTime

class AddNoteViewModel (
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val repository: AddAndEditNoteRepository
) :
    ViewModel() {


    private val _uiState = MutableStateFlow(AddNoteUiState())

    init {
        getCategories()
    }

    val uiState: StateFlow<AddNoteUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _uiState.value
    )

    fun updateNoteId(noteId: String){
        _uiState.update { it.copy(noteId = noteId) }
        getNoteById()
    }
    fun getNoteById() {
        viewModelScope.launch(Dispatchers.IO) {
            getNoteByIdUseCase(noteId = uiState.value.noteId).collectLatest { result ->
                result?.let { note ->
                    val formattedDateTime =
                        if (note.dueDateTime.isNotEmpty()) note.dueDateTime.getFormattedDateTime() else null
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            note = note,
                            dueDate = formattedDateTime?.date,
                            dueTime = TimeModel(
                                formattedDateTime?.time?.hour ?: 0,
                                formattedDateTime?.time?.minute ?: 0
                            ),
                            selectedCategory = _uiState.value.categories.find { category -> category.id == note.category },
                            actionMessage = "Updated",
                        )
                    }
                }
            }
        }
    }

    fun updateNote(note: Note) {
        _uiState.update { it.copy(note = note, error = "", validationDataError = "") }
    }

    fun updateDueDate(date: LocalDate) {
        _uiState.update { it.copy(dueDate = date) }
    }

    fun updateDueTime(time: TimeModel) {
        _uiState.update { it.copy(dueTime = time) }
    }

    fun addNote() {
        viewModelScope.launch(Dispatchers.IO) {
            tryGenerateDueDateTime()

            val id = if (uiState.value.noteId.isEmpty()) {
                addNoteUseCase(
                    title = _uiState.value.note.title,
                    description = _uiState.value.note.description ?: "",
                    dueDateTime = _uiState.value.note.dueDateTime,
                    isCompleted = false,
                    category = _uiState.value.selectedCategory?.id ?: 1
                )
            } else {
                updateNoteUseCase(
                    noteId = uiState.value.noteId,
                    title = _uiState.value.note.title,
                    description = _uiState.value.note.description ?: "",
                    dueDateTime = _uiState.value.note.dueDateTime,
                    isCompleted = _uiState.value.note.isCompleted,
                    category = _uiState.value.selectedCategory?.id ?: 1
                )
                uiState.value.noteId
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    createOrUpdateNoteStatus = true,
                    actionMessage = if (id.equals(
                            uiState.value.noteId,
                            ignoreCase = true
                        )
                    ) "Updated" else "Added",
                )
            }
        }
    }

    private fun tryGenerateDueDateTime() {
        _uiState.value.dueDate?.generateDueDateTime(
            hour = _uiState.value.dueTime.hour,
            minute = _uiState.value.dueTime.minute
        )?.let { dueDateTime ->
            _uiState.update { it.copy(note = _uiState.value.note.copy(dueDateTime = dueDateTime)) }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collectLatest { result ->
                _uiState.update {
                    it.copy(
                        categories = result,
                        selectedCategory = result[0]
                    )
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateValidationErrorContent() {
        _uiState.update { it.copy(validationDataError = "") }
    }

}

@Stable
data class AddNoteUiState(
    val note: Note = Note(),
    val noteId: String = "",
    val createOrUpdateNoteStatus: Boolean = false,
    val dueTime: TimeModel = TimeModel(),
    val dueDate: LocalDate? = null,
    val actionMessage: String = "Added",
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val validationDataError: String = "",
)