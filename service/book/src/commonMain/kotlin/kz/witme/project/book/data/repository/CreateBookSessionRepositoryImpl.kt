package kz.witme.project.book.data.repository

import kz.witme.project.book.data.model.request.toDto
import kz.witme.project.book.data.network.CreateBookSessionApi
import kz.witme.project.book.domain.model.CreateSessionBody
import kz.witme.project.book.domain.repository.CreateBookSessionRepository
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall

internal class CreateBookSessionRepositoryImpl(
    private val api: CreateBookSessionApi
) : CreateBookSessionRepository {

    override suspend fun createSession(
        id: String,
        body: CreateSessionBody
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        api.createSession(
            id = id,
            body = body.toDto()
        )
    }
}