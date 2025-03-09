package kz.witme.project.common_ui.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class SharedImage(
    private val context: Context, // Needed to access the image file from Uri
    private val bitmap: Bitmap?,
    private val uri: Uri
) {
    actual fun toByteArray(): ByteArray? {
        if (bitmap == null) {
            println("toByteArray null")
            return null
        }

        // Fix orientation using EXIF metadata
        val correctedBitmap = fixImageOrientation(context, bitmap, uri)

        // Compress the corrected bitmap
        val outputStream = ByteArrayOutputStream()
        correctedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        return outputStream.toByteArray()
    }

    actual fun toImageBitmap(): ImageBitmap? = bitmap?.asImageBitmap()

    private fun fixImageOrientation(context: Context, bitmap: Bitmap, uri: Uri): Bitmap {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val exif = ExifInterface(inputStream!!)
            val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
            inputStream.close()

            if (rotation == 0f) return bitmap // No rotation needed

            val matrix = Matrix().apply { postRotate(rotation) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            println("Error fixing image orientation: ${e.message}")
            bitmap
        }
    }
}
