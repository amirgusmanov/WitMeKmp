package kz.witme.project.book.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.book.data.local.runtime.BooksStorage
import kz.witme.project.book.data.network.CreateBookApi
import kz.witme.project.book.data.network.CreateBookSessionApi
import kz.witme.project.book.data.network.GetBookApi
import kz.witme.project.book.data.network.GetBookDetailsApi
import kz.witme.project.book.data.network.createCreateBookApi
import kz.witme.project.book.data.network.createCreateBookSessionApi
import kz.witme.project.book.data.network.createGetBookApi
import kz.witme.project.book.data.network.createGetBookDetailsApi
import kz.witme.project.book.data.repository.CreateBookRepositoryImpl
import kz.witme.project.book.data.repository.CreateBookSessionRepositoryImpl
import kz.witme.project.book.data.repository.GetBookRepositoryImpl
import kz.witme.project.book.data.repository.GetBookSessionDetailsRepositoryImpl
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.book.domain.repository.CreateBookSessionRepository
import kz.witme.project.book.domain.repository.GetBookRepository
import kz.witme.project.book.domain.repository.GetBookSessionDetailsRepository
import org.koin.dsl.module

val serviceBookModule = module {
    single<GetBookApi> { get<Ktorfit>().createGetBookApi() }
    single<CreateBookApi> { get<Ktorfit>().createCreateBookApi() }
    single<CreateBookSessionApi> { get<Ktorfit>().createCreateBookSessionApi() }
    single<GetBookDetailsApi> { get<Ktorfit>().createGetBookDetailsApi() }
    single<BooksStorage> { BooksStorage() }
    single<GetBookRepository> { GetBookRepositoryImpl(api = get(), storage = get()) }
    single<CreateBookRepository> { CreateBookRepositoryImpl(api = get()) }
    single<CreateBookSessionRepository> { CreateBookSessionRepositoryImpl(api = get()) }
    single<GetBookSessionDetailsRepository> { GetBookSessionDetailsRepositoryImpl(api = get()) }
}