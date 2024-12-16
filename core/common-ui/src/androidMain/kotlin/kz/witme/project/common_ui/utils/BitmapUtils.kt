package kz.witme.project.common_ui.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

object BitmapUtils {

    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap? {
        val inputStream: InputStream?
        try {
            inputStream = contentResolver.openInputStream(uri)
            val s = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            return s
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}