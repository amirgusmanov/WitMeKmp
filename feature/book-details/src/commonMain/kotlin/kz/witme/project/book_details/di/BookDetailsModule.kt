package kz.witme.project.book_details.di

import kz.witme.project.book_details.details.DetailsViewModel
import kz.witme.project.book_details.details_session.SessionDetailsInfoViewModel
import org.koin.dsl.module

val featureBookDetailsModule = module {
    factory { DetailsViewModel(sessionRepository = get()) }
    factory { SessionDetailsInfoViewModel() }
}