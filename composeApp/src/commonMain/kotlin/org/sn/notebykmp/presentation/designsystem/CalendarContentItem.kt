package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sn.notebykmp.domain.model.CalendarUiModel


@Composable
fun CalendarContentItem(
    date: CalendarUiModel.Date,
    onClickListener: (CalendarUiModel.Date) -> Unit,
) {

    val backgroundColor = if (date.isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Unspecified
    }

    val textStyle = if (date.isSelected) {
        MaterialTheme.typography.bodySmall.copy(color =  MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
    } else {
        MaterialTheme.typography.bodyLarge.copy(color =  Color.Unspecified, fontSize = 14.sp)
    }
    Surface(
        onClick = { onClickListener(date) },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = date.day.first().uppercase(),
                style = textStyle
            )
            HorizontalDivider()
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(
                        color = if (date.isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.date.dayOfMonth.toString(),
                    style = textStyle,
                )
            }
        }
    }
}