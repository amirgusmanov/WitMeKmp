package kz.witme.project.common_ui.theme

import androidx.compose.ui.graphics.Brush

val LinearGradient = Brush.verticalGradient(
    colors = listOf(
        lightPalette.textBrush100,
        lightPalette.textBrush200,
    )
)

val TimerLinearGradient = Brush.verticalGradient(
    colors = listOf(
        lightPalette.textBrush100,
        lightPalette.textBrush100,
        lightPalette.textBrush200.copy(alpha = 0.75f),
    )
)