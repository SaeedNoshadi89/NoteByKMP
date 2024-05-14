package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    showTimePicker: Boolean,
    modifier: Modifier = Modifier,
    timePickerState: (TimePickerState) -> Unit,
    onDismiss: () -> Unit
) {

    val timeState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0
    )
    if (showTimePicker) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = modifier
                    .background(color = Color.White)
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(
                    state = timeState,
                    colors = TimePickerDefaults.colors(
                        selectorColor = MaterialTheme.colorScheme.primary,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.surface,
                        periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                        periodSelectorSelectedContentColor = MaterialTheme.colorScheme.surface
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = {
                        onDismiss()
                        timePickerState(timeState)
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }

}