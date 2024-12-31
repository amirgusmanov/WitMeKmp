package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface Destination : ScreenProvider {
    data object Onboarding : Destination
    data object Login : Destination
    data object Registration : Destination
    data object EditProfile : Destination
    data object Dashboard : Destination
    data object CreateBook : Destination
    data object Profile : Destination
    data class CreateStatusBook(val args: CreateBookArgs) : Destination
    data class Timer(val bookId: String? = null) : Destination
    data class TimerDetails(
        val bookName: String,
        val seconds: Long,
        val readingStatus: String,
        val previousPage: Int,
        val maxPages: Int
    ) : Destination
}