package kz.witme.project.book_details.di

import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.book_details.details.DetailsViewModel
import kz.witme.project.book_details.details_session.SessionDetailsInfoViewModel
import org.koin.dsl.module

val featureBookDetailsModule = module {
    factory { (book: GetBook) ->
        DetailsViewModel(
            sessionRepository = get(),
            book = book
        )
    }
    factory { (session: GetBookSessionDetails, bookName: String) ->
        SessionDetailsInfoViewModel(
            session = session,
            bookName = bookName
        )
    }
}