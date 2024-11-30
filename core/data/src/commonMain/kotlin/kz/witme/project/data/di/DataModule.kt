package kz.witme.project.data.di

import io.ktor.client.HttpClient
import kz.witme.project.data.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect val dataModule: Module

val sharedDataModule = module {
    single<HttpClient> { HttpClientFactory.create(get()) }
}