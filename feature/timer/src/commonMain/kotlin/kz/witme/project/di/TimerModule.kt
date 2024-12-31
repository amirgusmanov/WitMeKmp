package kz.witme.project.di

import kz.witme.project.timer.TimerViewModel
import kz.witme.project.timer_details.TimerDetailsViewModel
import org.koin.dsl.module

val featureTimerModule = module {
    factory { TimerViewModel(booksRepository = get()) }
    factory { TimerDetailsViewModel(createBookSessionRepository = get()) }
}