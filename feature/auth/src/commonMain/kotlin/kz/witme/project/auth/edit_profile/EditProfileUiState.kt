package kz.witme.project.auth.edit_profile

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap

@Stable
internal data class EditProfileUiState(
    val nameQuery: String = "",
    val updateErrorMessage: String = "",
    val isUpdateLoading: Boolean = false,
    val isUpdateButtonEnabled: Boolean = true,
    val imageBitmap: ImageBitmap? = null,
    val launchCamera: Boolean = false,
    val launchGallery: Boolean = false,
    val launchSettings: Boolean = false,
    val showRationalDialog: Boolean = false,
    val isAvatarPickOptionBottomSheetVisible: Boolean = false
)