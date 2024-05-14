package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sn.notebykmp.domain.model.Category


@Composable
fun SegmentedControl(
    modifier: Modifier = Modifier,
    items: List<Category>,
    height: Dp,
    containerColor: Color = MaterialTheme.colorScheme.background,
    unSelectedSegmentColor: Color = MaterialTheme.colorScheme.background,
    selectedSegmentColor: Color = MaterialTheme.colorScheme.primary,
    selectedTextColor: Color = MaterialTheme.colorScheme.surface,
    unSelectedTextColor: Color = LocalTextStyle.current.color,
    hasDivider: Boolean = false,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius: Int = 38,
    onItemSelection: (selectedItemIndex: Category) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(defaultSelectedItemIndex) }
    var itemIndex by remember { mutableIntStateOf(defaultSelectedItemIndex) }

    DisposableEffect(key1 = Unit) {
        if (items.isNotEmpty()){
            onItemSelection(items[selectedIndex])
        }
        onDispose {  }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(containerColor),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                itemIndex = index
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = {
                        selectedIndex = index
                        onItemSelection(item)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedIndex == index) {
                            selectedSegmentColor
                        } else {
                            unSelectedSegmentColor
                        },
                    ),
                    shape = RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = cornerRadius
                    ),
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp,
                                fontWeight = if (selectedIndex == index)
                                    FontWeight.SemiBold
                                else
                                    FontWeight.Medium,
                                color = if (selectedIndex == index)
                                    selectedTextColor
                                else
                                    unSelectedTextColor
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                if (hasDivider && index < items.lastIndex && selectedIndex != index && selectedIndex != index.plus(
                        1
                    )
                ) {
                    Box(modifier = Modifier.padding(vertical = 8.dp)) {
                        VerticalDivider(color = selectedSegmentColor)
                    }
                }
            }
        }
    }

}
