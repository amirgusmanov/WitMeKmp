package kz.witme.project.book.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface GetBookRepository {

    suspend fun updateBooks(): RequestResult<Unit, DataError.Remote>

    suspend fun observeBooks(): Flow<List<GetBook>>
}