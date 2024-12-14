package kz.witme.project.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class LoginViewModel(
    private val authRepository: AuthRepository
) : ScreenModel, LoginController {

    val uiState: StateFlow<LoginUiState> = MutableStateFlow(LoginUiState())

    override fun onEmailQueryChanged(query: String) {
        uiState.tryToUpdate {
            it.copy(emailQuery = query)
        }
    }

    override fun onPasswordQueryChanged(query: String) {
        uiState.tryToUpdate {
            it.copy(passwordQuery = query)
        }
    }

    override fun onLoginClick() {
        screenModelScope.launch {
            if (uiState.value.emailQuery.isBlank() || uiState.value.passwordQuery.isBlank()) {
                return@launch
            }
            startLoginLoading()
            authRepository.login(
                email = uiState.value.emailQuery,
                password = uiState.value.passwordQuery
            )
                .onSuccess {
                    uiState.tryToUpdate {
                        it.copy(isLoginSuccess = true)
                    }
                }
                .onError { error ->
                    uiState.tryToUpdate {
                        it.copy(loginErrorMessage = error.getMessage())
                    }
                }
                .also {
                    stopLoginLoading()
                }
        }
    }

    override fun onLoginErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(loginErrorMessage = "")
        }
    }

    private fun startLoginLoading() {
        uiState.tryToUpdate {
            it.copy(
                isLoginButtonEnabled = false,
                isLoginButtonLoading = true
            )
        }
    }

    private fun stopLoginLoading() {
        uiState.tryToUpdate {
            it.copy(
                isLoginButtonEnabled = true,
                isLoginButtonLoading = false
            )
        }
    }
}