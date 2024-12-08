package kz.witme.project.auth.login

import androidx.compose.runtime.Stable

@Stable
internal data class LoginUiState(
    val emailQuery: String = "",
    val passwordQuery: String = "",
    val loginErrorMessage: String = "",
    val isLoginButtonEnabled: Boolean = true,
    val isLoginSuccess: Boolean = false,
    val isLoginButtonLoading: Boolean = false,
)