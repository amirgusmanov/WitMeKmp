package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.GetBookSessionDetails

sealed interface Destination : ScreenProvider {
    data object Splash : Destination
    data object Onboarding : Destination
    data object Login : Destination
    data object Registration : Destination
    data object EditProfile : Destination
    data object Dashboard : Destination
    data object CreateBook : Destination
    data object Profile : Destination
    data class CreateStatusBook(val args: CreateBookArgs) : Destination //todo check here, try to save image in repository
    data class Timer(
        val bookId: String? = null,
        val isNavigatedFromTabs: Boolean = false
    ) : Destination
    data class TimerDetails(
        val book: GetBook, //todo check here
        val seconds: Long,
        val notes: List<String>
    ) : Destination
    data class BookDetails(val book: GetBook) : Destination
    data class BookSessionDetails(
        val bookSessionDetails: GetBookSessionDetails,
        val bookName: String
    ) : Destination
    data class WebViewScreen(
        val title: String,
        val link: String
    ) : Destination
}