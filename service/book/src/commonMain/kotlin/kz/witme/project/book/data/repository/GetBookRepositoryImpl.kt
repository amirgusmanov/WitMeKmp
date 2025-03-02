package kz.witme.project.book.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.witme.project.book.data.local.runtime.BooksStorage
import kz.witme.project.book.data.model.response.toDomain
import kz.witme.project.book.data.network.GetBookApi
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.repository.GetBookRepository
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall

internal class GetBookRepositoryImpl(
    private val api: GetBookApi,
    private val storage: BooksStorage
) : GetBookRepository {

    override suspend fun updateBooks(): RequestResult<Unit, DataError.Remote> {
        return safeCall {
            api.getBooks().also {
                storage.updateBooks(it)
            }
        }
    }

    override suspend fun observeBooks(): Flow<List<GetBook>> {
        return storage.books.map {
            it.map { response ->
                response.toDomain()
            }
        }
    }
}