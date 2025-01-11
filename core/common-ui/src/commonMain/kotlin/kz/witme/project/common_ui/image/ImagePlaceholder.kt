package kz.witme.project.common_ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.LocalWitMeTheme

@Composable
fun ImagePlaceholder(
    modifier: Modifier = Modifier,
    bookName: String,
    textStyle: TextStyle = LocalWitMeTheme.typography.medium16
) {
    Box(modifier = modifier) {
        Image(
            painter = ColorPainter(LocalWitMeTheme.colors.bookBg),
            contentDescription = "Atomic Habits",
            modifier = Modifier.matchParentSize()
        )
        Text(
            text = bookName,
            style = textStyle,
            color = LocalWitMeTheme.colors.placeholderText,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}