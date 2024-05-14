package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.calendar
import org.jetbrains.compose.resources.painterResource
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.monthAbbreviations
import org.sn.notebykmp.weekAbbreviations
import org.sn.notebykmp.withLeadingZero

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CalendarRow(
    date: CalendarUiModel.Date,
) {
    val dateText = if (date.isToday) "Today" else "${date.date.dayOfMonth.withLeadingZero()} ${monthAbbreviations[date.date.monthNumber]}"
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(Res.drawable.calendar),
                contentDescription = "calendar",
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                text = dateText,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = "${weekAbbreviations[date.date.dayOfWeek.name]}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline,
                )
            )
        }
    }
}