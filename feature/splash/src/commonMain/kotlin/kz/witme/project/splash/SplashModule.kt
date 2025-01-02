package kz.witme.project.splash

import org.koin.dsl.module

val featureSplashModule = module {
    factory { SplashViewModel(authRepository = get()) }
}