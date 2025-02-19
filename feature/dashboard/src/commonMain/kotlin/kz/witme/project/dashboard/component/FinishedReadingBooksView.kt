package kz.witme.project.dashboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.image.ImagePlaceholder
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.data.network.getImageUrl

@Composable
internal fun FinishedReadingBooksView(
    books: ImmutableList<GetBook>,
    modifier: Modifier = Modifier,
    onBookClick: (GetBook) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = books,
                key = { _, book ->
                    book.id
                }
            ) { index, book ->
                FinishedBookView(
                    modifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 8.dp,
                        end = if (index == books.lastIndex) 16.dp else 8.dp
                    ),
                    photo = book.bookPhoto,
                    name = book.name,
                    onBookClick = {
                        onBookClick(book)
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
    name: String,
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
        SubcomposeAsyncImage(
            model = getImageUrl(photo),
            contentDescription = "Atomic Habits",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(DefaultRoundedShape)
        ) {
            val state by painter.state.collectAsState()
            when (state) {
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                else -> ImagePlaceholder(
                    modifier = Modifier.matchParentSize(),
                    bookName = name
                )
            }
        }
    }
}