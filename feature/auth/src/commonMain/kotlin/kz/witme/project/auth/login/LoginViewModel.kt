package kz.witme.project.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate

internal class LoginViewModel(
//    private val authRepository: AuthRepository
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
//            val response = authRepository.login(
//                email = uiState.value.emailQuery,
//                password = uiState.value.passwordQuery
//            )
            delay(2000L)
            stopLoginLoading()
//            when (response) {
//                is RequestResult.Error -> {
//                    uiState.tryToUpdate {
//                        it.copy(loginErrorMessage = response.error?.message.toString())
//                    }
//                }
//
//                is RequestResult.Success -> {
//                    uiState.tryToUpdate {
//                        it.copy(isLoginSuccess = true)
//                    }
//                }
//            }
            uiState.tryToUpdate {
                it.copy(isLoginSuccess = true)
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