package kz.witme.project.profile_runtime_storage.di

import kz.witme.project.profile_runtime_storage.data.local.UserProfileRuntimeStorage
import org.koin.dsl.module

val serviceProfileRuntimeStorageModule = module {
    single {
        UserProfileRuntimeStorage()
    }
}