package kz.witme.project.book.data.repository

import kz.witme.project.book.data.model.response.GetBookResponse
import kz.witme.project.book.data.model.response.toDomain
import kz.witme.project.book.data.network.GetBookApi
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.repository.GetBookRepository
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall

internal class GetBookRepositoryImpl(
    private val api: GetBookApi
) : GetBookRepository {

    override suspend fun getBooks(): RequestResult<List<GetBook>, DataError.Remote> = safeCall {
        api.getBooks().map(GetBookResponse::toDomain)
    }
}