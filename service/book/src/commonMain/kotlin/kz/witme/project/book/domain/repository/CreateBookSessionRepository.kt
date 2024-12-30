package kz.witme.project.book.domain.repository

import kz.witme.project.book.domain.model.CreateSessionBody
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface CreateBookSessionRepository {

    suspend fun createSession(
        id: String,
        body: CreateSessionBody
    ): RequestResult<Unit, DataError.Remote>
}