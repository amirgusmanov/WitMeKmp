package kz.witme.project.book_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kz.witme.project.common_ui.image.getImageUrl
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.pages

@Composable
fun BookDataSectionView(
    modifier: Modifier = Modifier,
    photo: String,
    author: String,
    maxPages: Int
) {
    Row(
        modifier = modifier
    ) {
        AsyncImage(
            model = getImageUrl(photo),
            contentDescription = "Atomic Habits",
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(LocalWitMeTheme.colors.secondary300),
            error = ColorPainter(LocalWitMeTheme.colors.secondary300),
            fallback = ColorPainter(LocalWitMeTheme.colors.secondary300),
            modifier = Modifier
                .requiredSize(width = 110.dp, height = 160.dp)
                .clip(DefaultRoundedShape)
        )
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