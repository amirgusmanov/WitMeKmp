package kz.witme.project.create_book.di

import kz.witme.project.create_book.main.CreateBookViewModel
import kz.witme.project.create_book.main_status.CreateBookStatusViewModel
import org.koin.dsl.module

val featureCreateBookModule = module {
    factory { CreateBookViewModel() }
    factory { CreateBookStatusViewModel(repository = get()) }
}