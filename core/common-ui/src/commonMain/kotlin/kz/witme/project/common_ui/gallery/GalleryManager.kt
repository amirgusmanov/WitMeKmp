package kz.witme.project.common_ui.gallery

import androidx.compose.runtime.Composable
import kz.witme.project.common_ui.image.SharedImage

expect class GalleryManager(onLaunch: () -> Unit) {
    fun launch()
}

@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager