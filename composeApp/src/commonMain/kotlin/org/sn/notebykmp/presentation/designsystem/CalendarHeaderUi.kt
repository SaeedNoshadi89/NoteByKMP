package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.today
import org.jetbrains.compose.resources.stringResource
import org.sn.notebykmp.domain.model.CalendarUiModel

@Composable
internal fun CalendarHeaderUi(
    modifier: Modifier,
    date: CalendarUiModel,
    onTodayClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarHeader(
            data = date,
        )
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable { onTodayClick() }
        ) {
            Text(
                text = stringResource(Res.string.today),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary),
            )
        }
    }
}