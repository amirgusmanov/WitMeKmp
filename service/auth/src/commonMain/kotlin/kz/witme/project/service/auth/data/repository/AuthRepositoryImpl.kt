package kz.witme.project.service.auth.data.repository

import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kz.witme.project.service.auth.data.network.AuthApi
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getMe(): RequestResult<Unit, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun navigateUser(): RequestResult<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }
}