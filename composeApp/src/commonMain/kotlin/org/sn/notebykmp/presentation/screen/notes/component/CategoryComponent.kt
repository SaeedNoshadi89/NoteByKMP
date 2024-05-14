package org.sn.notebykmp.presentation.screen.notes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.archive_minus
import org.jetbrains.compose.resources.painterResource
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.presentation.designsystem.SegmentedControl
import org.sn.notebykmp.presentation.screen.notes.NotesUiState

@Composable
internal fun CategoryComponent(
    modifier: Modifier,
    uiState: NotesUiState,
    onSelectCategory: (Category) -> Unit,
    onNavigateToBookmark: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SegmentedControl(
            modifier = modifier.weight(1f),
            items = uiState.categories,
            hasDivider = false,
            height = 38.dp,
            defaultSelectedItemIndex = 0
        ) { item ->
            onSelectCategory(item)
        }
        IconButton(onClick = {onNavigateToBookmark()}){
            Icon(
                painter = painterResource(Res.drawable.archive_minus),
                contentDescription = "save",
                tint = Color.Unspecified
            )
        }
    }
}