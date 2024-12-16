package kz.witme.project.auth.edit_profile

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess
import kz.witme.project.profile.domain.repository.ProfileUpdateRepository

internal class EditProfileViewModel(
    private val profileUpdateRepository: ProfileUpdateRepository
) : ScreenModel, EditProfileController {

    val uiState: StateFlow<EditProfileUiState> = MutableStateFlow(EditProfileUiState())

    private val imageByteArray: StateFlow<ByteArray?> = MutableStateFlow(null)

    override fun onNameQueryChange(query: String) {
        uiState.tryToUpdate {
            it.copy(nameQuery = query)
        }
    }

    override fun onErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(updateErrorMessage = "")
        }
    }

    override fun onNextButtonClick() {
        screenModelScope.launch {
            startUpdateLoading()
            profileUpdateRepository.uploadAvatar(
                avatar = imageByteArray.value ?: return@launch,
                username = uiState.value.nameQuery
            )
                .onSuccess {
                    uiState.tryToUpdate {
                        it.copy(isEditProfileSuccess = true)
                    }
                }
                .onError { error ->
                    uiState.tryToUpdate {
                        it.copy(updateErrorMessage = error.getMessage())
                    }
                }
                .also {
                    stopUpdateLoading()
                }
        }
    }

    override fun onCameraLaunch() {
        uiState.tryToUpdate {
            it.copy(
                launchCamera = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    override fun onGalleryLaunch() {
        uiState.tryToUpdate {
            it.copy(
                launchGallery = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    override fun onSettingsLaunch() {
        uiState.tryToUpdate {
            it.copy(launchSettings = true)
        }
    }

    override fun onSettingsLaunched() {
        uiState.tryToUpdate {
            it.copy(launchSettings = false)
        }
    }

    override fun onRationalDialogShow() {
        uiState.tryToUpdate {
            it.copy(showRationalDialog = true)
        }
    }

    override fun onRationalDialogDismiss() {
        uiState.tryToUpdate {
            it.copy(showRationalDialog = false)
        }
    }

    override fun onAvatarClick() {
        uiState.tryToUpdate {
            it.copy(isAvatarPickOptionBottomSheetVisible = true)
        }
    }

    override fun onAvatarPickOptionBottomSheetDismiss() {
        uiState.tryToUpdate {
            it.copy(isAvatarPickOptionBottomSheetVisible = false)
        }
    }

    override fun onCameraPermissionAsk() {
        uiState.tryToUpdate {
            it.copy(launchCamera = false)
        }
    }

    override fun onGalleryPermissionAsk() {
        uiState.tryToUpdate {
            it.copy(launchGallery = false)
        }
    }

    override fun onAvatarPick(image: ImageBitmap, imageByteArray: ByteArray) {
        uiState.tryToUpdate { it.copy(imageBitmap = image) }
        this.imageByteArray.tryToUpdate { imageByteArray }
    }

    override fun onAvatarClear() {
        uiState.tryToUpdate {
            it.copy(imageBitmap = null)
        }
    }

    private fun startUpdateLoading() {
        uiState.tryToUpdate {
            it.copy(
                isUpdateLoading = true,
                isUpdateButtonEnabled = false,
            )
        }
    }

    private fun stopUpdateLoading() {
        uiState.tryToUpdate {
            it.copy(
                isUpdateLoading = false,
                isUpdateButtonEnabled = true,
            )
        }
    }
}