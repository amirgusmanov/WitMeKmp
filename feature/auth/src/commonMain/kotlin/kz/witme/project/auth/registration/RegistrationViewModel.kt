package kz.witme.project.auth.registration

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

internal class RegistrationViewModel(
    private val authRepository: AuthRepository
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
            authRepository.register(
                email = uiState.value.emailQuery,
                password = uiState.value.passwordQuery
            )
                .onSuccess {
                    uiState.tryToUpdate {
                        it.copy(isRegistrationSuccess = true)
                    }
                }
                .onError { error ->
                    uiState.tryToUpdate {
                        it.copy(registrationErrorMessage = error.getMessage())
                    }
                }
                .also {
                    stopRegistrationLoading()
                }
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