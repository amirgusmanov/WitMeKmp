package kz.witme.project.profile.profile

import androidx.compose.runtime.Stable
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class ProfileViewModel(
    //private val profileRepository: ProfileRepository
) : ScreenModel, ProfileController {

    val uiState: StateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())

    override fun onPrivacyPolicyClick() {}

    override fun onDeleteAccountClick() {}

    override fun onExitClick() {}
}