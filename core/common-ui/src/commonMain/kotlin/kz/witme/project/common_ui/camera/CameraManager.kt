package kz.witme.project.common_ui.camera

import androidx.compose.runtime.Composable
import kz.witme.project.common_ui.image.SharedImage

expect class CameraManager(onLaunch: () -> Unit) {
    fun launch()
}

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager