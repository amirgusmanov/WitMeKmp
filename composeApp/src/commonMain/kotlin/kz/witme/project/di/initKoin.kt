package kz.witme.project.di

import kz.witme.project.auth.di.featureAuthModule
import kz.witme.project.book.di.serviceBookModule
import kz.witme.project.book_details.di.featureBookDetailsModule
import kz.witme.project.create_book.di.featureCreateBookModule
import kz.witme.project.dashboard.di.featureDashboardModule
import kz.witme.project.data.di.platformDataModule
import kz.witme.project.data.di.sharedDataModule
import kz.witme.project.profile.di.sharedServiceProfileModule
import kz.witme.project.profile.profile.featureProfileModule
import kz.witme.project.service.auth.di.serviceAuthModule
import kz.witme.project.splash.featureSplashModule
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
            featureProfileModule,
            sharedServiceProfileModule,
            featureDashboardModule,
            serviceBookModule,
            featureCreateBookModule,
            featureTimerModule,
            featureBookDetailsModule,
            featureSplashModule
        )
    }
}