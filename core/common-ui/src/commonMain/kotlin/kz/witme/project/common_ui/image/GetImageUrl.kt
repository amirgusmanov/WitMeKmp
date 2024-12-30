package kz.witme.project.common_ui.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

@ReadOnlyComposable
@Composable
fun getImageUrl(url: String?): String = "https://witbook.ddns.net/$url"