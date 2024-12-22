package kz.witme.project.dashboard.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.common_ui.base.DefaultAddCardView
import kz.witme.project.common_ui.screen.getScreenWidth
import kz.witme.project.dashboard.BookEntry
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.add_book
import kotlin.math.absoluteValue

@Composable
internal fun BookPager(
    modifier: Modifier = Modifier,
    books: ImmutableList<BookEntry>,
    pagerState: PagerState,
    onBookClick: (bookId: String) -> Unit = {},
    onTimerClick: (bookId: String) -> Unit = {},
) {
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
            when (book) {
                is BookEntry.Book -> {
                    BookCardView(
                        id = book.bookResponse.id,
                        imageUrl = book.bookResponse.bookPhoto,
                        name = book.bookResponse.name,
                        date = book.bookResponse.createdDate.toString(), //todo: parse date here with DateUtils
                        status = book.bookResponse.readingStatus.displayName,
                        notes = book.bookResponse.notesAmount,
                        onBookClick = onBookClick,
                        onTimerClick = onTimerClick,
                        modifier = Modifier.defaultDashboardCardModifier(
                            pagerState = pagerState,
                            pageIndex = pageIndex
                        )
                    )
                }

                BookEntry.Empty -> DefaultAddCardView(
                    text = stringResource(Res.string.add_book),
                    modifier = Modifier.defaultDashboardCardModifier(
                        pagerState = pagerState,
                        pageIndex = pageIndex
                    )
                )
            }
        }
    }
}

@Stable
@Composable
private fun Modifier.defaultDashboardCardModifier(
    pagerState: PagerState,
    pageIndex: Int
) = width((getScreenWidth().value * 0.8).dp)
    .heightIn(min = 190.dp, max = 215.dp)
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
