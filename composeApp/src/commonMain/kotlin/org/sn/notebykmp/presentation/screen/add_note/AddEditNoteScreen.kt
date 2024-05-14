@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package org.sn.notebykmp.presentation.screen.add_note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.eraser
import notebykmp.composeapp.generated.resources.link
import notebykmp.composeapp.generated.resources.smallcaps
import notebykmp.composeapp.generated.resources.undo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.sn.notebykmp.domain.model.TimeModel
import org.sn.notebykmp.presentation.designsystem.DatePickerInDatePickerDialog
import org.sn.notebykmp.presentation.designsystem.NoteBasicTextField
import org.sn.notebykmp.presentation.designsystem.TimePickerWithDialog
import org.sn.notebykmp.presentation.screen.add_note.component.CategorySection
import org.sn.notebykmp.presentation.screen.add_note.component.ConfirmAddDialog
import org.sn.notebykmp.presentation.screen.add_note.component.DateTimeSheet
import org.sn.notebykmp.presentation.screen.add_note.component.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onShowSnackbar: suspend (String) -> Boolean,
    noteId: String?,
) {
    val viewModel = koinInject<AddNoteViewModel>()
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val showTimePicker = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember { mutableStateOf(false) }

    val openDatePicker = remember {
        mutableStateOf(false)
    }
    val selectedTextLength = remember {
        mutableIntStateOf(0)
    }

    var titleValue: TextFieldValue by remember { mutableStateOf(value = TextFieldValue()) }
    var descriptionValue: TextFieldValue by remember { mutableStateOf(value = TextFieldValue()) }

    var openEditContentsHolder by remember {
        mutableStateOf(mapOf("" to false))
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    var onEraseText by remember {
        mutableStateOf(false)
    }

    var shouldEraseTitleContent by remember {
        mutableStateOf("")
    }
    var shouldEraseDescriptionContent by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        if (noteId.isNullOrEmpty().not())
            viewModel.updateNoteId(noteId ?: "")
    }

    LaunchedEffect(key1 = onEraseText) {
        if (onEraseText) {
            titleValue =
                if (openEditContentsHolder.containsKey("title")) TextFieldValue(
                    titleValue.text.replace(
                        shouldEraseTitleContent,
                        ""
                    )
                ) else titleValue

            descriptionValue =
                if (openEditContentsHolder.containsKey("description")) TextFieldValue(
                    descriptionValue.text.replace(
                        shouldEraseDescriptionContent,
                        ""
                    )
                ) else descriptionValue

            openEditContentsHolder = mapOf("" to false)
        }
    }
    LaunchedEffect(key1 = openEditContentsHolder) {
        if (openEditContentsHolder.containsValue(true)) {
            onEraseText = false
            delay(500)
            keyboardController?.hide()
        }
    }


    LaunchedEffect(key1 = state.createOrUpdateNoteStatus) {
        if (state.createOrUpdateNoteStatus) {
            onBack()
            onShowSnackbar(state.actionMessage)

        }
    }
    LaunchedEffect(key1 = state.error) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error)
        }
    }
    LaunchedEffect(key1 = state.note) {
        titleValue = TextFieldValue(state.note.title)
        descriptionValue = TextFieldValue(state.note.description ?: "")
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val maxHeight = maxHeight
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(28.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Header(onBack = {
                onBack()
            }) {
                scope.launch {
                    keyboardController?.hide()
                    delay(200)
                    showBottomSheet.value = true
                }
            }
            CategorySection(uiState = state) {
                viewModel.selectCategory(it)
            }
            Surface(
                modifier = modifier.weight(1f).padding(bottom = 20.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    NoteBasicTextField(
                        value = titleValue,
                        updateTextValue = {
                            titleValue = it
                            selectedTextLength.intValue =
                                it.selection.max - it.selection.min
                            shouldEraseTitleContent = it.getSelectedText().text
                            openEditContentsHolder =
                                mapOf("title" to (selectedTextLength.intValue > 0))
                        },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                        placeholder = "Enter title",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )

                    NoteBasicTextField(
                        modifier = modifier.weight(1f),
                        value = descriptionValue,
                        updateTextValue = {
                            descriptionValue = it
                            selectedTextLength.intValue =
                                it.selection.max - it.selection.min
                            shouldEraseDescriptionContent = it.getSelectedText().text
                            openEditContentsHolder =
                                mapOf("description" to (selectedTextLength.intValue > 0))
                        },
                        textStyle = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
                        placeholder = "Enter description",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )

                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        DatePickerInDatePickerDialog(
                            openDialog = openDatePicker.value,
                            onDismiss = { openDatePicker.value = false },
                            selectedDate = { date ->
                                date?.let {
                                    if (Instant.fromEpochMilliseconds(it)
                                            .toLocalDateTime(TimeZone.currentSystemDefault()).date < Clock.System.now()
                                            .toLocalDateTime(
                                                TimeZone.currentSystemDefault()
                                            ).date
                                    ) {
                                        scope.launch {
                                            onShowSnackbar("Date is not valid")
                                        }
                                        return@let
                                    }
                                    viewModel.updateDueDate(
                                        Instant.fromEpochMilliseconds(it)
                                            .toLocalDateTime(
                                                TimeZone.currentSystemDefault()
                                            ).date
                                    )
                                    scope.launch {
                                        onShowSnackbar("Date is selected")
                                    }
                                }
                            })
                    }
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TimePickerWithDialog(
                            showTimePicker = showTimePicker.value,
                            timePickerState = {
                                viewModel.updateDueTime(
                                    time = TimeModel(
                                        it.hour,
                                        it.minute
                                    )
                                )
                            },
                            onDismiss = { showTimePicker.value = false })

                    }

                    Box(
                        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            modifier = modifier.fillMaxWidth(),
                            onClick = {
                            viewModel.apply {
                                updateNote(
                                    state.note.copy(
                                        title = titleValue.text,
                                        description = descriptionValue.text
                                    )
                                )
                            }
                            openDialog.value = true
                        }) {
                            Text(text = if (state.noteId.isEmpty()) "Add" else "Update")
                        }
                    }
                }
            }
        }
        DateTimeSheet(
            showBottomSheet,
            modifier,
            maxHeight,
            sheetState,
            state,
            openDatePicker,
            showTimePicker
        )

        ConfirmAddDialog(
            modifier = modifier,
            openDialog = openDialog.value,
            onConfirm = {
                openDialog.value = false
                viewModel.addNote()
            }, onDismiss = {
                openDialog.value = false
                onBack()
            })

        AnimatedVisibility(visible = openEditContentsHolder.containsValue(true)) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(41.dp),
                    color = MaterialTheme.colorScheme.scrim,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = modifier.clickable { onEraseText = true },
                                painter = painterResource(Res.drawable.eraser),
                                contentDescription = "edit",
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Icon(
                                painter = painterResource(Res.drawable.link),
                                contentDescription = "link",
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Icon(
                                painter = painterResource(Res.drawable.smallcaps),
                                contentDescription = "caps",
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Icon(
                                painter = painterResource(Res.drawable.undo),
                                contentDescription = "undo",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                        Box(
                            modifier = modifier
                                .clickable {
                                    openEditContentsHolder = mapOf("" to false)
                                }
                                .size(42.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }

}