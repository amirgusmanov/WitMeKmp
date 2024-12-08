package kz.witme.project.auth.di

import kz.witme.project.auth.login.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    factory {
        LoginViewModel()
    }
}