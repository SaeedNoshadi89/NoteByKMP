package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import org.sn.notebykmp.domain.model.CalendarUiModel

@Composable
fun CalendarContent(
    lazyState: LazyListState = rememberLazyListState(),
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    LazyRow(state = lazyState) {
        items(items = data.visibleDates) { date ->
            CalendarContentItem(
                date = date,
                onClickListener = { onDateClickListener(it) }
            )
        }
    }
}