package kz.witme.project.book_details.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.book_details.details.DetailsScreen
import kz.witme.project.book_details.details_session.SessionDetailsInfoScreen
import kz.witme.project.navigation.Destination

val bookDetailsNavigationModule = screenModule {
    register<Destination.BookDetails> { provider ->
        DetailsScreen(book = provider.book)
    }
    register<Destination.BookSessionDetails> { provider ->
        SessionDetailsInfoScreen(
            session = provider.bookSessionDetails,
            bookName = provider.bookName
        )
    }
}