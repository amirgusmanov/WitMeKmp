package kz.witme.project.profile.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess
import kz.witme.project.profile.domain.repository.ProfileUpdateRepository
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val updateProfileRepository: ProfileUpdateRepository
) : ScreenModel, ProfileController {

    val uiState: StateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())

    private val _responseEventFlow = Channel<ResponseEvent>()
    val responseEventFlow = _responseEventFlow.receiveAsFlow()

    init {
        collectUserInfo()
    }

    private fun collectUserInfo() {
        screenModelScope.launch {
            authRepository.getUserInfoFlow().collectLatest { userInfo ->
                if (userInfo == null) {
                    authRepository.navigateUser()
                } else {
                    uiState.tryToUpdate {
                        it.copy(
                            username = userInfo.username.orEmpty(),
                            avatar = userInfo.avatar.orEmpty()
                        )
                    }
                }
            }
        }
    }

    override fun onExitClick() {
        uiState.tryToUpdate {
            it.copy(
                showLogoutAlert = true
            )
        }
    }

    override fun onExitAlertDismiss() {
        uiState.tryToUpdate {
            it.copy(
                showLogoutAlert = false
            )
        }
    }

    override fun onExitAlertConfirm() {
        screenModelScope.launch {
            authRepository.logout()
        }
    }

    override fun onDeleteAccountClick() {
        uiState.tryToUpdate {
            it.copy(
                showDeleteAccountAlert = true
            )
        }
    }

    override fun onDeleteAccountAlertDismiss() {
        uiState.tryToUpdate {
            it.copy(
                showDeleteAccountAlert = false
            )
        }
    }

    override fun onDeleteAccountAlertConfirm() {
        screenModelScope.launch {
            updateProfileRepository.deleteAccount()
                .onSuccess {
                    HttpClientFactory.navigateFlow.tryToUpdate {
                        HttpClientFactory.NavigateFlow.LoginFlow
                    }
                }
                .onError { error ->
                    uiState.tryToUpdate {
                        it.copy(errorMessage = error.getMessage())
                    }
                }
        }
    }

    override fun onPrivacyPolicyClick() {
        screenModelScope.launch {
            _responseEventFlow.send(ResponseEvent.NavigateToPrivacyPolicy)
        }
    }

    override fun onErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(
                errorMessage = ""
            )
        }
    }

    fun onSettingsLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(launchSettings = true)
        }
    }

    fun onCameraLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(
                launchCamera = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    fun onGalleryLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(
                launchGallery = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    fun onSettingsLaunched() {
        uiState.tryToUpdate { state ->
            state.copy(launchSettings = false)
        }
    }

    fun onRationalDialogShow() {
        uiState.tryToUpdate { state ->
            state.copy(showRationalDialog = true)
        }
    }

    fun onRationalDialogDismiss() {
        uiState.tryToUpdate { state ->
            state.copy(showRationalDialog = false)
        }
    }

    override fun onAvatarClick() {
        uiState.tryToUpdate { state ->
            state.copy(isAvatarPickOptionBottomSheetVisible = true)
        }
    }

    fun onAvatarPickOptionBottomSheetDismiss() {
        uiState.tryToUpdate { state ->
            state.copy(isAvatarPickOptionBottomSheetVisible = false)
        }
    }

    fun onCameraPermissionAsk() {
        uiState.tryToUpdate { state ->
            state.copy(launchCamera = false)
        }
    }

    fun onGalleryPermissionAsk() {
        uiState.tryToUpdate { state ->
            state.copy(launchGallery = false)
        }
    }

    fun onBookPhotoPicked(byteArray: ByteArray) {
        screenModelScope.launch {
            updateProfileRepository.uploadAvatar(
                avatar = byteArray,
                username = uiState.value.username.ifBlank { return@launch }
            ).onSuccess {
                authRepository.navigateUser()
            }.onError { error ->
                uiState.tryToUpdate {
                    it.copy(errorMessage = error.getMessage())
                }
            }
        }
    }

    sealed interface ResponseEvent {
        data object NavigateToPrivacyPolicy : ResponseEvent
    }
}