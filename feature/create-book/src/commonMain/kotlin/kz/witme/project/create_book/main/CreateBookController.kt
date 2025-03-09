package kz.witme.project.create_book.main

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.Flow

@Stable
internal interface CreateBookController {

    val createBookNavigateFlow: Flow<CreateBookViewModel.CreateBookNavigateChannel>

    fun onAuthorQueryChanged(query: String)

    fun onBookNameQueryChanged(query: String)

    fun onBookListCountQueryChanged(query: String)

    fun onBookPhotoPicked(
        image: ImageBitmap,
        byteArray: ByteArray
    )

    fun onRationalDialogDismiss()

    fun onAvatarClick()

    fun onSettingsLaunch()

    fun onCameraLaunch()

    fun onRationalDialogShow()

    fun onAvatarPickOptionBottomSheetDismiss()

    fun onCameraPermissionAsk()

    fun onSettingsLaunched()

    fun onNextButtonClick()
}