@file:OptIn(ExperimentalComposeUiApi::class)

package kz.witme.project.common_ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@Composable
@Stable
actual fun getScreenHeight(): Dp = with(LocalDensity.current) {
    LocalWindowInfo.current
        .containerSize
        .width
        .toDp()
}