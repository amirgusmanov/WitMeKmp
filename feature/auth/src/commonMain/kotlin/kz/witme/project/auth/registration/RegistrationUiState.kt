package kz.witme.project.auth.registration

import androidx.compose.runtime.Stable

@Stable
internal data class RegistrationUiState(
    val emailQuery: String = "",
    val passwordQuery: String = "",
    val registrationErrorMessage: String = "",
    val isRegistrationButtonEnabled: Boolean = true,
    val isRegistrationSuccess: Boolean = false,
    val isRegistrationButtonLoading: Boolean = false,
)