package kz.witme.project.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.image.getImageUrl
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.dashboard.BookEntry
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_plus

@Composable
internal fun ToReadBooksView(
    bookEntries: ImmutableList<BookEntry>,
    modifier: Modifier = Modifier,
    onBookClick: (String) -> Unit,
    onEmptyClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .offset(y = 16.dp)
                .padding(vertical = 1.dp),
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(containerColor = LocalWitMeTheme.colors.white),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(modifier = Modifier.height(42.dp))
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .offset(y = (-16).dp)
                .zIndex(1f),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(items = bookEntries) { book ->
                ToReadBook(
                    bookEntry = book,
                    onBookClick = {
                        onBookClick((book as BookEntry.Book).bookResponse.id)
                    },
                    onEmptyClick = onEmptyClick
                )
            }
        }
    }
}

@Composable
private fun ToReadBook(
    bookEntry: BookEntry,
    modifier: Modifier = Modifier,
    onBookClick: (String) -> Unit,
    onEmptyClick: () -> Unit
) {
    when (bookEntry) {
        is BookEntry.Book ->
            Box(
                modifier = modifier
                    .size(60.dp)
                    .clickableWithoutRipple {
                        onBookClick(bookEntry.bookResponse.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = getImageUrl(bookEntry.bookResponse.bookPhoto),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    placeholder = ColorPainter(color = LocalWitMeTheme.colors.secondary300),
                    error = ColorPainter(color = LocalWitMeTheme.colors.secondary300),
                    fallback = ColorPainter(color = LocalWitMeTheme.colors.secondary300)
                )
            }

        BookEntry.Empty -> EmptyView(onClick = onEmptyClick)
    }
}

@Composable
private fun EmptyView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(LocalWitMeTheme.colors.primary200)
            .padding(2.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(LocalWitMeTheme.colors.white),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.ic_plus),
                    tint = LocalWitMeTheme.colors.primary200,
                    contentDescription = null,
                )
            }
        }
    }
}