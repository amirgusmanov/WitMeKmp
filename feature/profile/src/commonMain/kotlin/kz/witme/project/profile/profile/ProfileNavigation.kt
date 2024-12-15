package kz.witme.project.profile.profile

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.navigation.Destination

val featureProfileNavigationModule = screenModule {
    register<Destination.Profile> { ProfileScreen() }
}