package org.sn.notebykmp.presentation.screen.add_note.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sn.notebykmp.presentation.screen.add_note.AddNoteUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateTimeSheet(
    showBottomSheet: MutableState<Boolean>,
    modifier: Modifier,
    maxHeight: Dp,
    sheetState: SheetState,
    uiState: AddNoteUiState,
    openDatePicker: MutableState<Boolean>,
    showTimePicker: MutableState<Boolean>
) {
    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = modifier
                .heightIn(max = maxHeight.div(3f))
                .safeDrawingPadding(),
            onDismissRequest = {
                showBottomSheet.value = false
            },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
        ) {
            ModalSection(
                uiState = uiState,
                onPickDate = { openDatePicker.value = true },
                onPickTime = { showTimePicker.value = true })
        }
    }
}