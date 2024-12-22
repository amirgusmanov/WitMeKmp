package kz.witme.project.common_ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun Modifier.toolbarPaddings(): Modifier = padding(horizontal = 20.dp).padding(top = 44.dp)