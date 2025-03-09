package kz.witme.project.common_ui.image

import androidx.compose.ui.graphics.ImageBitmap

expect class SharedImage {
    suspend fun toByteArray(): ByteArray?
    suspend fun toImageBitmap(): ImageBitmap?
}