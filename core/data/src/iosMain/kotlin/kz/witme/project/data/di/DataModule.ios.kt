package kz.witme.project.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
    }