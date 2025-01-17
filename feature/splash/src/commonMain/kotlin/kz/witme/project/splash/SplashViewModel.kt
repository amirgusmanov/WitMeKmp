package kz.witme.project.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class SplashViewModel(
    private val authRepository: AuthRepository
) : ScreenModel {

    init {
        navigateUser()
    }

    private fun navigateUser() {
        screenModelScope.launch {
            authRepository.navigateUser()
        }
    }
}