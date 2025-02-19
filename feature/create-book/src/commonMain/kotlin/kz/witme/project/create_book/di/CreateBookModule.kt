package kz.witme.project.create_book.di

import kz.witme.project.create_book.main.CreateBookViewModel
import kz.witme.project.create_book.main_status.CreateBookStatusViewModel
import kz.witme.project.navigation.CreateBookArgs
import org.koin.dsl.module

val featureCreateBookModule = module {
    factory { CreateBookViewModel(createBookRepository = get()) }
    factory { (args: CreateBookArgs) ->
        CreateBookStatusViewModel(
            repository = get(),
            navArgs = args
        )
    }
}