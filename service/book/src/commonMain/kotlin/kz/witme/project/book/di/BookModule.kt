package kz.witme.project.book.di

import de.jensklingenberg.ktorfit.Ktorfit
import kz.witme.project.book.data.network.GetBookApi
import kz.witme.project.book.data.network.createGetBookApi
import kz.witme.project.book.data.repository.GetBookRepositoryImpl
import kz.witme.project.book.domain.repository.GetBookRepository
import org.koin.dsl.module

val serviceBookModule = module {
    single<GetBookApi> { get<Ktorfit>().createGetBookApi() }
    single<GetBookRepository> { GetBookRepositoryImpl(api = get()) }
}