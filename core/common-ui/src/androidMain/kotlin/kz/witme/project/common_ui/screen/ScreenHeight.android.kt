package kz.witme.project.common_ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Stable
actual fun getScreenHeight(): Dp = LocalConfiguration.current
    .screenHeightDp
    .dp