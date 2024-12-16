package kz.witme.project.auth.edit_profile

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.common.log.Logger

internal class EditProfileViewModel(
//    private val profileRepository: ProfileRepository
) : ScreenModel, EditProfileController {

    val uiState: StateFlow<EditProfileUiState> = MutableStateFlow(EditProfileUiState())

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
//        if (uiState.value.avatarUri.isNull() || uiState.value.nameQuery.isBlank()) return
        screenModelScope.launch {
            startUpdateLoading()
//            val response = profileRepository.updateProfile(
//                name = uiState.value.nameQuery,
//                avatarUri = uiState.value.avatarUri!!
//            )
            stopUpdateLoading()
//            when (response) {
//                is RequestResult.Error -> {
//                    uiState.tryToUpdate {
//                        it.copy(updateErrorMessage = response.error?.message.toString())
//                    }
//                }
//
//                is RequestResult.Success -> {
//                    uiState.tryToUpdate {
//                        it.copy(isEditProfileSuccess = true)
//                    }
//                }
//            }
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

    override fun onAvatarPick(image: ImageBitmap) {
        Logger.e("Avatar", "$image")
        uiState.tryToUpdate {
            it.copy(imageBitmap = image)
        }
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