package kz.witme.project.common_ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun Modifier.toolbarPaddings(): Modifier = padding(horizontal = 20.dp)
    .padding(
        top = WindowInsets.statusBars
            .only(WindowInsetsSides.Top)
            .asPaddingValues()
            .calculateTopPadding()
    )