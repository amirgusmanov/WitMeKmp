package kz.witme.project.di

import kz.witme.project.timer.model.TimerViewModel
import org.koin.dsl.module

val featureTimerModule = module {
    factory { TimerViewModel(booksRepository = get()) }
}