package org.sn.notebykmp.presentation.screen.add_note.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.back
import notebykmp.composeapp.generated.resources.calendar
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Header(modifier: Modifier = Modifier, onBack: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier.clickable { onBack() },
            painter = painterResource(Res.drawable.back),
            contentDescription = "back"
        )

        Icon(
            modifier = modifier.clickable { onSave() },
            painter = painterResource(Res.drawable.calendar),
            contentDescription = "archive circle",
            tint = Color.Unspecified
        )

    }
}