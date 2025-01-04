package kz.witme.project.book.data.repository

import kz.witme.project.book.data.model.response.toDomain
import kz.witme.project.book.data.network.GetBookDetailsApi
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.book.domain.repository.GetBookSessionDetailsRepository
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall

internal class GetBookSessionDetailsRepositoryImpl(
    private val api: GetBookDetailsApi
) : GetBookSessionDetailsRepository {

    override suspend fun getBookSessionDetails(
        bookId: String
    ): RequestResult<List<GetBookSessionDetails>, DataError.Remote> = safeCall {
        api.getSessionDetails(bookId).map { it.toDomain() }
    }
}