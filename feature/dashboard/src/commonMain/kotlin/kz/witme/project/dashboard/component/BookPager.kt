@file:OptIn(ExperimentalComposeUiApi::class)

package kz.witme.project.dashboard.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.common_ui.screen.getScreenWidth
import kz.witme.project.dashboard.Book
import kotlin.math.absoluteValue

@Composable
internal fun BookPager(
    modifier: Modifier = Modifier,
    books: ImmutableList<Book>,
    pagerState: PagerState,
    onBookClick: (bookId: String) -> Unit = {},
    onTimerClick: (bookId: String) -> Unit = {},
) {
    //todo посоветоваться с дизайнером насчет ux хаптики при скролле пейджера
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(pagerState.currentPage) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = (getScreenWidth().value * 0.1).dp)
    ) { pageIndex ->
        books.getOrNull(pageIndex)?.let { book ->
            BookCardView(
                id = book.name,
                imageUrl = book.imageUri,
                name = book.name,
                date = book.startDate.toString(),
                status = book.bookStatus.name,
                notes = book.rating.toString(),
                onBookClick = onBookClick,
                onTimerClick = onTimerClick,
                modifier = Modifier
                    .width((getScreenWidth().value * 0.8).dp)
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction)
                                .absoluteValue
                        val scale = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}