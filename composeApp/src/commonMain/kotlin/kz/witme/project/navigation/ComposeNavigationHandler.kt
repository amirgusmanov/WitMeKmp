package kz.witme.project.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kz.witme.project.data.network.NavigationHandler

class ComposeNavigationHandler : NavigationHandler {

    private val _navigationEvents = MutableSharedFlow<Destination>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    override suspend fun navigateToOnboarding() {
        _navigationEvents.emit(Destination.Onboarding)
    }
}