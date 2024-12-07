package kz.witme.project

import androidx.compose.ui.window.ComposeUIViewController
import kz.witme.project.di.initKoin
import kz.witme.project.navigation.NavRegistry

fun MainViewController() = ComposeUIViewController(
    configure = {
        NavRegistry.registerScreens()
        initKoin()
    }
) {
    App()
}