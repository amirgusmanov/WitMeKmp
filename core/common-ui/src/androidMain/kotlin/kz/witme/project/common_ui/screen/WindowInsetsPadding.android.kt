package kz.witme.project.common_ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
actual fun bottomNavigationPaddings(): Dp = WindowInsets.navigationBars
    .only(WindowInsetsSides.Bottom)
    .asPaddingValues()
    .calculateBottomPadding()