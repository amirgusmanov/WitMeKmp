package kz.witme.project.book.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.book.data.network.CreateBookApi
import kz.witme.project.book.data.network.GetBookApi
import kz.witme.project.book.data.network.createCreateBookApi
import kz.witme.project.book.data.network.createGetBookApi
import kz.witme.project.book.data.repository.CreateBookRepositoryImpl
import kz.witme.project.book.data.repository.GetBookRepositoryImpl
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.book.domain.repository.GetBookRepository
import org.koin.dsl.module

val serviceBookModule = module {
    single<GetBookApi> { get<Ktorfit>().createGetBookApi() }
    single<CreateBookApi> { get<Ktorfit>().createCreateBookApi() }
    single<GetBookRepository> { GetBookRepositoryImpl(api = get()) }
    single<CreateBookRepository> { CreateBookRepositoryImpl(api = get()) }
}