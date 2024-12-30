package kz.witme.project.profile.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.profile.data.network.ProfileUpdateApi
import kz.witme.project.profile.data.network.createProfileUpdateApi
import kz.witme.project.profile.data.repository.ProfileUpdateRepositoryImpl
import kz.witme.project.profile.domain.repository.ProfileUpdateRepository
import org.koin.dsl.module

val sharedServiceProfileModule = module {
    single<ProfileUpdateApi> { get<Ktorfit>().createProfileUpdateApi() }
    single<ProfileUpdateRepository> { ProfileUpdateRepositoryImpl(get()) }
}