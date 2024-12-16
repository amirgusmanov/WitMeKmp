package kz.witme.project.common_ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kz.witme.project.common_ui.R
import java.io.File
import java.util.Objects

class ComposeFileProvider : FileProvider(R.xml.path_provider) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val tempFile = File.createTempFile(
                "picture_${System.currentTimeMillis()}", ".jpeg", context.cacheDir
            ).apply {
                createNewFile()
            }
            val authority = context.applicationContext.packageName + ".provider"
            return getUriForFile(Objects.requireNonNull(context), authority, tempFile)
        }
    }
}