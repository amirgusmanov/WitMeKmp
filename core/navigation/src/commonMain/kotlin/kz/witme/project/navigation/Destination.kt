package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface Destination : ScreenProvider {
    data object Onboarding : Destination
    data object Login : Destination
    data object Registration : Destination
    data object EditProfile : Destination
    data object Dashboard : Destination
    data object CreateBook : Destination
    data class CreateStatusBook(val args: CreateBookArgs) : Destination
    data class Timer(val bookId: String? = null) : Destination
    data object TimerDetails : Destination
}