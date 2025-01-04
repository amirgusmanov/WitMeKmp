package kz.witme.project.dashboard.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.book.domain.model.GetBook
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
    onBookClick: (book: GetBook) -> Unit = {},
    onTimerClick: (bookId: String) -> Unit = {},
    onEmptyClick: () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.isScrollInProgress) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = (getScreenWidth().value * 0.1).dp)
    ) { pageIndex ->
        books.getOrNull(pageIndex)?.let { book ->
            val pageOffset = remember(
                pagerState.currentPage,
                pagerState.currentPageOffsetFraction
            ) {
                ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction)
                    .absoluteValue
            }
            when (book) {
                is BookEntry.Book -> {
                    BookCardView(
                        id = book.bookResponse.id,
                        imageUrl = book.bookResponse.bookPhoto,
                        name = book.bookResponse.name,
                        date = book.bookResponse.createdDate.toString(), //todo: parse date here with DateUtils
                        status = book.bookResponse.readingStatus.displayName,
                        notes = book.bookResponse.notesAmount,
                        onBookClick = {
                            onBookClick(book.bookResponse)
                        },
                        onTimerClick = onTimerClick,
                        modifier = Modifier.defaultDashboardCardModifier(pageOffset = pageOffset)
                    )
                }

                BookEntry.Empty -> DefaultAddCardView(
                    text = stringResource(Res.string.add_book),
                    modifier = Modifier.defaultDashboardCardModifier(pageOffset = pageOffset),
                    onClick = onEmptyClick
                )
            }
        }
    }
}

@Stable
@Composable
private fun Modifier.defaultDashboardCardModifier(
    pageOffset: Float
) = width((getScreenWidth().value * 0.8).dp)
    .heightIn(min = 190.dp, max = 215.dp)
    .graphicsLayer {
        val scale = lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        scaleX = scale
        scaleY = scale
    }
