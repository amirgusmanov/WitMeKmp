package kz.witme.project.di

import kz.witme.project.auth.di.featureAuthModule
import kz.witme.project.dashboard.di.featureDashboardModule
import kz.witme.project.data.di.platformDataModule
import kz.witme.project.data.di.sharedDataModule
import kz.witme.project.profile.di.sharedServiceProfileModule
import kz.witme.project.service.auth.di.serviceAuthModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            sharedDataModule,
            platformDataModule,
            featureAuthModule,
            serviceAuthModule,
            sharedServiceProfileModule,
            featureDashboardModule
        )
    }
}