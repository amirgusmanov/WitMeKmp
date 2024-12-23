package kz.witme.project.create_book.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_selected_star
import witmekmp.core.common_ui.generated.resources.ic_unselected_start

@Composable
internal fun StarRating(
    rating: Int,
    maxRating: Int = 5,
    filledStar: Painter = painterResource(Res.drawable.ic_selected_star),
    emptyStar: Painter = painterResource(Res.drawable.ic_unselected_start),
    minSize: Dp = 45.dp,
    maxSize: Dp = 65.dp,
    modifier: Modifier = Modifier,
    onStarClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (i in 1..maxRating) {
            val star = if (i <= rating) filledStar else emptyStar
            Image(
                painter = star,
                contentDescription = null,
                modifier = Modifier
                    .clickableWithoutRipple {
                        onStarClick(i)
                    }
                    .sizeIn(
                        minWidth = minSize,
                        minHeight = minSize,
                        maxWidth = maxSize,
                        maxHeight = maxSize
                    )
            )
        }
    }
}