package kz.witme.project.book.domain.repository

import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface GetBookSessionDetailsRepository {

    suspend fun getBookSessionDetails(
        bookId: String
    ): RequestResult<List<GetBookSessionDetails>, DataError.Remote>
}