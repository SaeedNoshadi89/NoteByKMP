package org.sn.notebykmp.presentation.screen.add_note.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.category
import org.jetbrains.compose.resources.painterResource
import org.sn.notebykmp.domain.model.Category
import org.sn.notebykmp.presentation.screen.add_note.AddNoteUiState

@Composable
internal fun CategorySection(
    modifier: Modifier = Modifier,
    uiState: AddNoteUiState,
    onCategory: (Category) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier.clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.category),
                    contentDescription = "category",
                    tint = Color.Unspecified
                )
                Text(
                    text = uiState.selectedCategory?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
        if (uiState.categories.isNotEmpty()) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                uiState.categories.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name, fontWeight = FontWeight.Bold) },
                        onClick = {
                            onCategory(it)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}