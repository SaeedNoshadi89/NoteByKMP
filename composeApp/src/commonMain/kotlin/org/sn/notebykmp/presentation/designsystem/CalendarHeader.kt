@file:OptIn(ExperimentalAnimationApi::class)

package org.sn.notebykmp.presentation.designsystem

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sn.notebykmp.domain.model.CalendarUiModel
import org.sn.notebykmp.monthAbbreviations
import org.sn.notebykmp.weekAbbreviations

@Composable
fun CalendarHeader(
    data: CalendarUiModel,
//    onPrevClickListener: (LocalDate) -> Unit,
//    onNextClickListener: (LocalDate) -> Unit,
) {
    val alphaAnimation = remember { Animatable(0f) }
    LaunchedEffect(data) {
        alphaAnimation.animateTo(1f)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = "${data.selectedDate.date.dayOfMonth}",
            transitionSpec = {
                fadeIn(animationSpec = tween(1500)) togetherWith
                        fadeOut(animationSpec = tween(1500))
            }, label = ""
        ) { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 44.sp)
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            AnimatedContent(
                targetState = "${weekAbbreviations[data.selectedDate.date.dayOfWeek.name]}",
                transitionSpec = {
                    fadeIn(animationSpec = tween(1500)) togetherWith
                            fadeOut(animationSpec = tween(1500))
                }, label = ""
            ) { text ->
                Text(
                    text = text,
                    style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 14.sp
                    )
                )
            }

            AnimatedContent(
                targetState = "${monthAbbreviations[data.selectedDate.date.monthNumber]} ${data.selectedDate.date.year}",
                transitionSpec = {
                    fadeIn(animationSpec = tween(1500)) togetherWith
                            fadeOut(animationSpec = tween(1500))
                }, label = ""
            ) { text ->
                Text(
                    text = text,
                    style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

