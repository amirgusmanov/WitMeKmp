package kz.witme.project.create_book.main

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap

@Stable
data class CreateBookMainUiState(
    val bookName: String = "",
    val authorName: String = "",
    val bookListCount: Int? = null,
    val photo: ImageBitmap? = null,
    val launchCamera: Boolean = false,
    val launchSettings: Boolean = false,
    val showRationalDialog: Boolean = false,
    val isAvatarPickOptionBottomSheetVisible: Boolean = false
)