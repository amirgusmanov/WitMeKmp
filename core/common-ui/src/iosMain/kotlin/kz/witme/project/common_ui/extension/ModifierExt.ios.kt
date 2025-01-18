package kz.witme.project.common_ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.Dp

@Composable
actual fun Modifier.blur(radius: Dp): Modifier = blur(radius)