package kz.witme.project.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.common_ui.screen.getScreenWidth
import kz.witme.project.common_ui.shimmer.ShimmerView
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.dashboard.component.BookPager
import kz.witme.project.dashboard.component.ToReadBooksView
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.finished_reading
import witmekmp.core.common_ui.generated.resources.to_read

@Composable
internal fun DashboardContent(
    modifier: Modifier = Modifier,
    currentlyReadingBooks: ImmutableList<BookEntry>,
    toReadBooks: ImmutableList<BookEntry>,
    finishedReadingBooks: ImmutableList<GetBook>,
    currentlyReadingBooksPager: PagerState,
    onEmptyClick: () -> Unit,
    onBookClick: (String) -> Unit,
    onTimerClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        BookPager(
            modifier = Modifier.fillMaxWidth(),
            books = currentlyReadingBooks,
            pagerState = currentlyReadingBooksPager,
            onBookClick = onBookClick,
            onTimerClick = onTimerClick
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(Res.string.to_read),
            color = LocalWitMeTheme.colors.secondary500,
            style = LocalWitMeTheme.typography.semiBold20,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ToReadBooksView(
            bookEntries = toReadBooks,
            modifier = Modifier.fillMaxWidth(),
            onBookClick = onBookClick,
            onEmptyClick = onEmptyClick
        )
        if (finishedReadingBooks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(Res.string.finished_reading),
                color = LocalWitMeTheme.colors.secondary500,
                style = LocalWitMeTheme.typography.semiBold20,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun DashboardLoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerView(
            modifier = Modifier
                .width((getScreenWidth().value * 0.8).dp)
                .heightIn(min = 190.dp, max = 215.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(Res.string.to_read),
            color = LocalWitMeTheme.colors.secondary500,
            style = LocalWitMeTheme.typography.semiBold20,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerView(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(Res.string.finished_reading),
            color = LocalWitMeTheme.colors.secondary500,
            style = LocalWitMeTheme.typography.semiBold20,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerView(modifier = Modifier.fillMaxWidth().height(150.dp).weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            ShimmerView(modifier = Modifier.fillMaxWidth().height(150.dp).weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            ShimmerView(modifier = Modifier.fillMaxWidth().height(150.dp).weight(1f))
        }
    }
}