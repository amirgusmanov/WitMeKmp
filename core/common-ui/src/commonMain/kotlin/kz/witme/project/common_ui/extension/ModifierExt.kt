package kz.witme.project.common_ui.extension

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role

//todo: add clickableWithPressedState
fun Modifier.clickableWithoutRipple(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = enabled,
        indication = null,
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
    )
}

fun Modifier.textBrush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcAtop)
        }
    }

fun Modifier.clickableWithPressedState(
    onClick: () -> Unit,
    scaleWhenPressed: Float = 0.9f,
    animationDuration: Int = 100
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleWhenPressed else 1f,
        animationSpec = tween(durationMillis = animationDuration)
    )
    return@composed scale(scale).pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                isPressed = true
                try {
                    awaitRelease()
                } finally {
                    isPressed = false
                    onClick()
                }
            }
        )
    }
}