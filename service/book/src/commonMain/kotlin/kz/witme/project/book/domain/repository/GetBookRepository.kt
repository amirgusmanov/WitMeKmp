package kz.witme.project.book.domain.repository

import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface GetBookRepository {

    suspend fun getBooks(): RequestResult<List<GetBook>, DataError.Remote>
}