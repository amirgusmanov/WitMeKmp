package kz.witme.project.service.auth.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.service.auth.data.network.AuthApi
import kz.witme.project.service.auth.data.network.createAuthApi
import kz.witme.project.service.auth.data.repository.AuthRepositoryImpl
import kz.witme.project.service.auth.data.storage.runtime.UserProfileRuntimeStorage
import kz.witme.project.service.auth.domain.repository.AuthRepository
import org.koin.dsl.module

val serviceAuthModule = module {
    single<AuthApi> { get<Ktorfit>().createAuthApi() }
    single<UserProfileRuntimeStorage> { UserProfileRuntimeStorage() }
    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
            sessionManager = get(),
            runtimeStorage = get()
        )
    }
}