package kz.witme.project.auth.edit_profile

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap

@Stable
internal interface EditProfileController {

    fun onNameQueryChange(query: String)

    fun onErrorDismiss()

    fun onNextButtonClick()

    fun onCameraLaunch()

    fun onGalleryLaunch()

    fun onSettingsLaunch()

    fun onSettingsLaunched()

    fun onRationalDialogShow()

    fun onRationalDialogDismiss()

    fun onAvatarClick()

    fun onAvatarPickOptionBottomSheetDismiss()

    fun onCameraPermissionAsk()

    fun onGalleryPermissionAsk()

    fun onAvatarPick(image: ImageBitmap, imageByteArray: ByteArray)

    fun onAvatarClear()
}