package kz.witme.project.di

import kz.witme.project.auth.di.authModule
import kz.witme.project.data.di.sharedDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            sharedDataModule,
            authModule
        )
    }
}