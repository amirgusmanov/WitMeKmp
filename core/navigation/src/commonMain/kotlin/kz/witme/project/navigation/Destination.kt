package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.witme.project.book.domain.model.GetBook

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
        val book: GetBook,
        val seconds: Long,
        val notes: List<String>
    ) : Destination
}