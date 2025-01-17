package kz.witme.project.book_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
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
import kz.witme.project.common_ui.image.ImagePlaceholder
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.data.network.getImageUrl
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.pages

@Composable
fun BookDataSectionView(
    modifier: Modifier = Modifier,
    name: String,
    photo: String?,
    author: String,
    maxPages: Int
) {
    Row(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = getImageUrl(photo),
            contentDescription = "Atomic Habits",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(width = 110.dp, height = 160.dp)
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
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = author,
                style = LocalWitMeTheme.typography.regular16,
                color = LocalWitMeTheme.colors.white
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$maxPages ${stringResource(Res.string.pages)}",
                style = LocalWitMeTheme.typography.regular16,
                color = LocalWitMeTheme.colors.primary200
            )
        }
    }
}