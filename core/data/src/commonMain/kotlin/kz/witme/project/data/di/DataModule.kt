package kz.witme.project.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.data.util.Constants
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataModule: Module

val sharedDataModule = module {
    single<Ktorfit> {
        Ktorfit.Builder()
            .httpClient(
                HttpClientFactory.create(
                    engine = get(),
                    sessionManager = get()
                )
            )
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}