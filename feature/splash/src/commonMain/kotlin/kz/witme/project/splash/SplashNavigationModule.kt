package kz.witme.project.splash

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.navigation.Destination

val splashNavigationModule = screenModule {
    register<Destination.Splash> { SplashScreen() }
}