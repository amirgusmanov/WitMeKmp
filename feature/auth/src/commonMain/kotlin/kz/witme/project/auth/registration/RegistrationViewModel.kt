package kz.witme.project.auth.registration

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.common.extension.tryToUpdate

internal class RegistrationViewModel(
//    private val authRepository: AuthRepository
) : ScreenModel, RegistrationController {

    val uiState: StateFlow<RegistrationUiState> = MutableStateFlow(RegistrationUiState())

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


    override fun onRegisterClick() {
        screenModelScope.launch {
            if (uiState.value.emailQuery.isBlank() || uiState.value.passwordQuery.isBlank()) {
                return@launch
            }
            startRegistrationLoading()
//            val response = authRepository.register(
//                email = uiState.value.emailQuery,
//                password = uiState.value.passwordQuery
//            )
            delay(2000L)
            stopRegistrationLoading()
//            when (response) {
//                is RequestResult.Error -> {
//                    uiState.tryToUpdate {
//                        it.copy(registrationErrorMessage = response.error?.message.toString())
//                    }
//                }
//
//                is RequestResult.Success -> {
//                    uiState.tryToUpdate {
//                        it.copy(isRegistrationSuccess = true)
//                    }
//                }
//            }
        }
    }

    override fun onRegistrationErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(registrationErrorMessage = "")
        }
    }

    private fun startRegistrationLoading() {
        uiState.tryToUpdate {
            it.copy(
                isRegistrationButtonEnabled = false,
                isRegistrationButtonLoading = true
            )
        }
    }

    private fun stopRegistrationLoading() {
        uiState.tryToUpdate {
            it.copy(
                isRegistrationButtonEnabled = true,
                isRegistrationButtonLoading = false
            )
        }
    }
}