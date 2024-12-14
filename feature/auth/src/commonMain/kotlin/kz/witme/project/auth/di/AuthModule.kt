package kz.witme.project.auth.di

import kz.witme.project.auth.login.LoginViewModel
import kz.witme.project.auth.registration.RegistrationViewModel
import org.koin.dsl.module

val featureAuthModule = module {
    factory { LoginViewModel(authRepository = get()) }
    factory { RegistrationViewModel(authRepository = get()) }
}