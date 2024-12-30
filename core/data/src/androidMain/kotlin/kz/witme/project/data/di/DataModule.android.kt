package kz.witme.project.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kz.witme.project.data.local.SessionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<SessionManager> { SessionManager(context = androidApplication()) }
    }