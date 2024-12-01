package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface Destination : ScreenProvider {
    data object Onboarding : Destination
    data object Auth : Destination
    data object Registration : Destination
    data object EditProfile : Destination
    data object Dashboard : Destination
    data object CreateBook : Destination
    data object CreateStatusBook : Destination
}