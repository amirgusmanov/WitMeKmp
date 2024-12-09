package kz.witme.project.auth.di

import kz.witme.project.auth.login.LoginViewModel
import kz.witme.project.auth.registration.RegistrationViewModel
import org.koin.dsl.module

val authModule = module {
    factory {
        LoginViewModel()
    }
    factory {
        RegistrationViewModel()
    }
}