package kz.witme.project.profile.profile

import org.koin.dsl.module

val featureProfileModule = module {
    factory {
        ProfileViewModel(
            authRepository = get(),
            updateProfileRepository = get()
        )
    }
}