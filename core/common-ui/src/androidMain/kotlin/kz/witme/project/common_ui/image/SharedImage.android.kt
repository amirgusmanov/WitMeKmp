package kz.witme.project.common_ui.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import java.io.File
import java.io.FileOutputStream

actual class SharedImage(
    private val context: Context,
    private val bitmap: Bitmap?,
    private val uri: Uri
) {
    actual suspend fun toByteArray(): ByteArray? {
        val compressedFile = Compressor.compress(context, uri.toFile(context)) {
            resolution(3000, 3000)
            quality(95)
            size(1024 * 1024) // 1MB max
        }

        // Read EXIF metadata to detect rotation
        val rotation = getExifRotation(compressedFile)

        // Rotate the compressed image if needed
        val rotatedFile = if (rotation != 0) {
            rotateImageFile(compressedFile, rotation)
        } else {
            compressedFile
        }

        return rotatedFile.readBytes()
    }

    actual suspend fun toImageBitmap(): ImageBitmap? = bitmap?.asImageBitmap()

    private fun Uri.toFile(context: Context): File {
        return File.createTempFile("compressed_", ".jpg", context.cacheDir)
            .apply {
                outputStream().use { outputStream ->
                    context.contentResolver.openInputStream(this@toFile)?.copyTo(outputStream)
                }
            }
    }

    private fun getExifRotation(file: File): Int {
        return try {
            val exif = ExifInterface(file.absolutePath)
            when (
                exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            ) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: Exception) {
            0
        }
    }

    private fun rotateImageFile(file: File, degrees: Int): File {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        // Save rotated image
        val rotatedFile = File(file.parent, "rotated_${file.name}")
        FileOutputStream(rotatedFile).use { outputStream ->
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        return rotatedFile
    }
}
