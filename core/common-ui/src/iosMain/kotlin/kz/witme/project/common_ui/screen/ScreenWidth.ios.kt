@file:OptIn(ExperimentalComposeUiApi::class)

package kz.witme.project.common_ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@Stable
@Composable
actual fun getScreenWidth(): Dp = with(LocalDensity.current) {
    LocalWindowInfo.current
        .containerSize
        .width
        .toDp()
}