package kz.witme.project.common_ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
expect fun Modifier.toolbarPaddings(): Modifier

@Composable
expect fun bottomNavigationPaddings(): Dp
