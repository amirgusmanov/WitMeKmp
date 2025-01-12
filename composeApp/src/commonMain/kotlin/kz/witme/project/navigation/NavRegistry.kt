package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenRegistry
import kz.witme.project.auth.navigation.featureAuthModule
import kz.witme.project.book_details.navigation.bookDetailsNavigationModule
import kz.witme.project.create_book.navigation.createBookNavigationModule
import kz.witme.project.dashboard.navigation.dashboardNavigationModule
import kz.witme.project.profile.profile.featureProfileNavigationModule
import kz.witme.project.splash.splashNavigationModule
import kz.witme.project.web_view.navigation.webViewNavigationModule

object NavRegistry {

    fun registerScreens() {
        ScreenRegistry {
            featureAuthModule()
            featureProfileNavigationModule()
            dashboardNavigationModule()
            createBookNavigationModule()
            timerNavigationModule()
            bookDetailsNavigationModule()
            splashNavigationModule()
            webViewNavigationModule()
        }
    }
}