@file:OptIn(ExperimentalForeignApi::class)

package kz.witme.project.common_ui.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

actual class SharedImage(private val image: UIImage?) {
    actual suspend fun toByteArray(): ByteArray? {
        if (image == null) return null
        val imageData = UIImageJPEGRepresentation(image, COMPRESSION_QUALITY)
            ?: throw IllegalArgumentException("image data is null")
        val bytes = imageData.bytes
            ?: throw IllegalArgumentException("image bytes is null")
        val length = imageData.length

        val data: CPointer<ByteVar> = bytes.reinterpret()
        return ByteArray(length.toInt()) { index -> data[index] }
    }

    actual suspend fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray() ?: return null
        return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0.85
    }
}