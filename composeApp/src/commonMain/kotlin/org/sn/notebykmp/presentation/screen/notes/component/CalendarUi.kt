package org.sn.notebykmp.presentation.screen.notes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.presentation.designsystem.CalendarContent
import org.sn.notebykmp.presentation.designsystem.CalendarHeaderUi
import org.sn.notebykmp.presentation.screen.notes.NotesUiState

@Composable
internal fun CalendarUi(
    modifier: Modifier,
    uiState: NotesUiState,
    lazyCalendarListState: LazyListState,
    onTodayClick: () -> Unit,
    onSelectDateClick: (CalendarUiModel.Date) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        uiState.calendar?.let { calendar ->
            CalendarHeaderUi(modifier, calendar) { onTodayClick() }
            CalendarContent(
                lazyState = lazyCalendarListState,
                data = calendar
            ) { date ->
                onSelectDateClick(date)
            }
        }
    }
}
