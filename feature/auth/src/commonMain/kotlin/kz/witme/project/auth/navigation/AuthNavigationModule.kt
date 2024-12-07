package kz.witme.project.auth.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.auth.onboarding.OnboardingScreen
import kz.witme.project.navigation.Destination

val featureAuthModule = screenModule {
    register<Destination.Onboarding> { OnboardingScreen() }
}