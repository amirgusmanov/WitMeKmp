package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenRegistry
import kz.witme.project.auth.navigation.featureAuthModule

object NavRegistry {

    fun registerScreens() {
        ScreenRegistry {
            featureAuthModule()
        }
    }
}