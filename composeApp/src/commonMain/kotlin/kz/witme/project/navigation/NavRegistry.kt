package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenRegistry
import kz.witme.project.auth.navigation.featureAuthModule
import kz.witme.project.profile.profile.featureProfileNavigationModule

object NavRegistry {

    fun registerScreens() {
        ScreenRegistry {
            featureAuthModule()
            featureProfileNavigationModule()
        }
    }
}