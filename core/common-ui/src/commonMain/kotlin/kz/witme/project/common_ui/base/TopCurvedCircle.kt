package kz.witme.project.common_ui.base

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import kz.witme.project.common_ui.theme.WitMeTheme
import kz.witme.project.common_ui.theme.lightPalette
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val CIRCLE_HEIGHT = -90f

@Composable
fun TopCurvedCircle(
    modifier: Modifier = Modifier,
    yOffset: Float = CIRCLE_HEIGHT,
    radiusDivisionNumber: Float = 1.5f,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            brush = Brush.verticalGradient(
                colors = listOf(
                    lightPalette.textBrush100,
                    lightPalette.textBrush200
                )
            ),
            radius = size.minDimension / radiusDivisionNumber,
            center = Offset(
                x = size.width / 2,
                y = yOffset
            )
        )
    }
}

@Preview
@Composable
private fun TopCurvedCirclePreview() {
    WitMeTheme {
        TopCurvedCircle()
    }
}