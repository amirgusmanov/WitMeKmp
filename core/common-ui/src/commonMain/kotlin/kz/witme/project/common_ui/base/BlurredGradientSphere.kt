package kz.witme.project.common_ui.base

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val COLOR_OPACITY = 0.3f
private const val ANIMATION_DURATION_IN_MILLIS = 1000
private const val INITIAL_TRANSITION_VALUE = 0.15f
private const val TARGET_TRANSITION_VALUE = 0.85f
private const val TRANSITION_ANIMATION_LABEL = "Infinite transition"
private const val POSITION_ANIMATION_LABEL = "Positional movement via diagonal"
private const val COLOR_ANIMATION_LABEL = "Color change"
private const val BLUR_RADIUS = 32

@Composable
fun BlurredGradientSphere(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val infiniteTransition = rememberInfiniteTransition(label = TRANSITION_ANIMATION_LABEL)
    val position by infiniteTransition.animateFloat(
        initialValue = INITIAL_TRANSITION_VALUE,
        targetValue = TARGET_TRANSITION_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ANIMATION_DURATION_IN_MILLIS,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = POSITION_ANIMATION_LABEL
    )
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Yellow.copy(alpha = COLOR_OPACITY),
        targetValue = Color.Green.copy(alpha = COLOR_OPACITY),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ANIMATION_DURATION_IN_MILLIS,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = COLOR_ANIMATION_LABEL
    )

    //todo: add alternative blur modifier for android 12 and lower versions
    Canvas(
        modifier = modifier.blur(BLUR_RADIUS.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2.5f
        val diagonalOffset = Offset(
            x = center.x - radius / 2 + (radius * position),
            y = center.y - radius / 2 + (radius * position)
        )

        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    Color.Blue.copy(alpha = COLOR_OPACITY),
                    Color.Transparent
                )
            ),
            radius = radius,
            center = center
        )
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(
                    animatedColor,
                    Color.Transparent
                ),
            ),
            radius = radius / 2,
            center = diagonalOffset
        )
    }
}

@Preview
@Composable
private fun SomePreview() {
    WitMeTheme {
        BlurredGradientSphere()
    }
}