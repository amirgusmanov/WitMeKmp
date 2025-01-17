package kz.witme.project.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.data.util.Constants
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataModule: Module

val sharedDataModule = module {
    single<HttpClient> {
        HttpClientFactory.create(
            engine = get(),
            sessionManager = get()
        )
    }
    single<Ktorfit> {
        Ktorfit.Builder()
            .httpClient(client = get())
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}