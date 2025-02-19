package kz.witme.project.di

import kotlinx.collections.immutable.ImmutableList
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.timer.TimerViewModel
import kz.witme.project.timer_details.TimerDetailsViewModel
import org.koin.dsl.module

val featureTimerModule = module {
    factory { TimerViewModel(booksRepository = get()) }
    factory { (book: GetBook, elapsedSeconds: Long, notes: ImmutableList<String>) ->
        TimerDetailsViewModel(
            createBookSessionRepository = get(),
            book = book,
            elapsedSeconds = elapsedSeconds,
            notes = notes,
        )
    }
}