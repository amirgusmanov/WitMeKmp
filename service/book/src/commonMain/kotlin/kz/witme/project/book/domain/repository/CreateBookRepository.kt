package kz.witme.project.book.domain.repository

import kz.witme.project.book.domain.model.CreateBookRequest
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface CreateBookRepository {

    suspend fun createBook(request: CreateBookRequest): RequestResult<Unit, DataError.Remote>
}