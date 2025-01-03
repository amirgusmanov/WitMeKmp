package kz.witme.project.profile.profile

import androidx.compose.runtime.Stable

@Stable
internal data class ProfileUiState(
    val username: String = "",
    val avatar: String = "",
    val email: String = "",
    val errorMessage: String = "",
    val showDeleteAccountAlert: Boolean = false,
    val showLogoutAlert: Boolean = false
)