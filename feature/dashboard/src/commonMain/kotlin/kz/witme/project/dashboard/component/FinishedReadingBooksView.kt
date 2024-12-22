package kz.witme.project.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme

@Composable
internal fun FinishedReadingBooksView(
    books: ImmutableList<GetBook>,
    modifier: Modifier = Modifier,
    onBookClick: (String) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = books,
                key = GetBook::id
            ) { book ->
                FinishedBookView(
                    photo = book.bookPhoto,
                    onBookClick = {
                        onBookClick(book.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun FinishedBookView(
    modifier: Modifier = Modifier,
    photo: String?,
    onBookClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .width(95.dp)
            .height(140.dp)
            .clickableWithoutRipple(onClick = onBookClick),
        colors = CardDefaults.cardColors().copy(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = DefaultRoundedShape
    ) {
        AsyncImage(
            model = photo,
            contentDescription = "Atomic Habits",
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(LocalWitMeTheme.colors.secondary300),
            error = ColorPainter(LocalWitMeTheme.colors.secondary300),
            fallback = ColorPainter(LocalWitMeTheme.colors.secondary300),
            modifier = Modifier
                .fillMaxSize()
                .clip(DefaultRoundedShape)
        )
    }
}