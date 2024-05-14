@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package org.sn.notebykmp.presentation.designsystem

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> CarouselRow(
    list: List<T>,
    itemContainerColor: Color = MaterialTheme.colorScheme.background,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 22.dp
    ),
    pageSpacing: Dp = 22.dp,
    isAutoScrolling: Boolean = true,
    autoScrollDuration: Long = 3_000L,
    content: @Composable (item: T, modifier: Modifier) -> Unit,
) {
    val pageCount = list.size
    val scope = rememberCoroutineScope()
    val pagerState: PagerState = rememberPagerState(pageCount = { pageCount })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {

                    if (isAutoScrolling){
                        delay(timeMillis = autoScrollDuration)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        scope.launch {
                            animateScrollToPage(
                                page = nextPage,
                                animationSpec = tween(
                                    durationMillis = 800
                                )
                            )
                        }
                        currentPageKey = nextPage
                    }

                }
            }
        }
    }

    BoxWithConstraints {
        val maxWidth = maxWidth
        HorizontalPager(
            modifier = Modifier.width(maxWidth.div(2.3f)),
            state = pagerState,
            contentPadding = contentPadding,
            userScrollEnabled = false,
            pageSpacing = pageSpacing,
        ) { page: Int ->
            val item = list[page]
            item?.let {
                Box(
                    modifier = Modifier
                        .background(itemContainerColor)
                        .carouselTransition(
                            page,
                            pagerState
                        )
                ) {
                    content(
                        item = item,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(
    page: Int,
    pagerState: PagerState
) = graphicsLayer {
    val pageOffset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

    val transformation = lerp(
        start = 0.8f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(
            0f,
            1f
        )
    )
    alpha = when {
        pageOffset < 0.5f -> 1f // Fully visible when not scrolling
        else -> transformation // Fade in/out during scrolling
    }
    scaleY = transformation
}