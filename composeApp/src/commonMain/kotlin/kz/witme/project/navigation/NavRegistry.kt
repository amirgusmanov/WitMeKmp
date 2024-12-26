package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenRegistry
import kz.witme.project.auth.navigation.featureAuthModule
import kz.witme.project.create_book.navigation.createBookNavigationModule
import kz.witme.project.dashboard.navigation.dashboardNavigationModule

object NavRegistry {

    fun registerScreens() {
        ScreenRegistry {
            featureAuthModule()
            dashboardNavigationModule()
            createBookNavigationModule()
            timerNavigationModule()
        }
    }
}