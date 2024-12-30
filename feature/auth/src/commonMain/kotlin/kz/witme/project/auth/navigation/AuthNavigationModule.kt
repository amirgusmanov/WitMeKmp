package kz.witme.project.auth.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.auth.edit_profile.EditProfileScreen
import kz.witme.project.auth.login.LoginScreen
import kz.witme.project.auth.onboarding.OnboardingScreen
import kz.witme.project.auth.registration.RegistrationScreen
import kz.witme.project.navigation.Destination

val featureAuthModule = screenModule {
    register<Destination.Onboarding> { OnboardingScreen() }
    register<Destination.Login> { LoginScreen() }
    register<Destination.Registration> { RegistrationScreen() }
    register<Destination.EditProfile> { EditProfileScreen() }
}