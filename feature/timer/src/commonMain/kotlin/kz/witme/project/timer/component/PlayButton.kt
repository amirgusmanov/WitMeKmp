package kz.witme.project.timer.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.screen.getScreenWidth
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kotlin.math.sqrt

private val timerColor = Color(0xFFBCD9FF)

@Composable
internal fun PlayButton(
    modifier: Modifier = Modifier,
    isRunning: Boolean,
    onTimerClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size((getScreenWidth().value * 0.4).dp)
            .background(Color.Transparent, CircleShape)
            .border(12.dp, LocalWitMeTheme.colors.primary200, CircleShape)
            .clickableWithPressedState(onClick = onTimerClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(fraction = 0.35f)
        ) {
            if (isRunning) drawPauseIcon() else drawPlayIcon()
        }
    }
}

private fun DrawScope.drawPlayIcon() {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val sideLength = size.width * 0.8f
    val height = (sqrt(3.0) / 2 * sideLength).toFloat()

    val trianglePath = Path().apply {
        moveTo(centerX, centerY - height / 2)
        lineTo(centerX - sideLength / 2, centerY + height / 2)
        lineTo(centerX + sideLength / 2, centerY + height / 2)
        close()
    }
    rotate(degrees = 90f) {
        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(trianglePath),
                paint = Paint().apply {
                    color = timerColor
                    pathEffect = PathEffect.cornerPathEffect(10f)
                }
            )
        }
    }
}

private fun DrawScope.drawPauseIcon() {
    val barWidth = size.width * 0.2f
    val barHeight = size.height * 0.8f

    drawRoundRect(
        color = timerColor,
        topLeft = Offset(
            x = size.width * 0.3f - barWidth / 2,
            y = size.height * 0.1f
        ),
        size = Size(barWidth, barHeight),
        cornerRadius = CornerRadius(x = 100f, y = 100f)
    )
    drawRoundRect(
        color = timerColor,
        topLeft = Offset(
            x = size.width * 0.7f - barWidth / 2,
            y = size.height * 0.1f
        ),
        size = Size(barWidth, barHeight),
        cornerRadius = CornerRadius(x = 100f, y = 100f)
    )
}