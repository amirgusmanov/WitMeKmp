package kz.witme.project.service.auth.data.repository

import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kz.witme.project.service.auth.data.model.request.AuthRequest
import kz.witme.project.service.auth.data.network.AuthApi
import kz.witme.project.service.auth.domain.model.UserInfo
import kz.witme.project.service.auth.domain.model.toUserInfo
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        api.login(registerRequest = AuthRequest(email = email, password = password))
    }

    override suspend fun register(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        api.register(registerRequest = AuthRequest(email = email, password = password))
    }

    override suspend fun getMe(): RequestResult<UserInfo, DataError.Remote> = safeCall {
        api.getMe().toUserInfo()
    }

    override suspend fun navigateUser(): RequestResult<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }
}