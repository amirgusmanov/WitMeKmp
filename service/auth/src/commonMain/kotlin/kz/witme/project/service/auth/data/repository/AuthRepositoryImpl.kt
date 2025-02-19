package kz.witme.project.service.auth.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import kotlinx.coroutines.flow.Flow
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.local.SessionManager
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kz.witme.project.profile_runtime_storage.data.local.UserProfileRuntimeStorage
import kz.witme.project.profile_runtime_storage.domain.model.UserInfo
import kz.witme.project.service.auth.data.model.request.AuthRequest
import kz.witme.project.service.auth.data.network.AuthApi
import kz.witme.project.service.auth.domain.mapper.toUserInfo
import kz.witme.project.service.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sessionManager: SessionManager,
    private val runtimeStorage: UserProfileRuntimeStorage,
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        val response = api.login(
            registerRequest = AuthRequest(email = email, password = password)
        )
        with(sessionManager) {
            setAccessToken(response.accessToken)
            setRefreshToken(response.refreshToken.orEmpty())
        }
        clearKtorTokens()
        navigateUser()
    }

    override suspend fun register(
        email: String,
        password: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        val response = api.register(
            registerRequest = AuthRequest(email = email, password = password)
        )
        with(sessionManager) {
            setAccessToken(response.accessToken)
            setRefreshToken(response.refreshToken.orEmpty())
        }
        clearKtorTokens()
        navigateUser()
    }

    override suspend fun navigateUser(): RequestResult<UserInfo, DataError.Remote> = safeCall {
        api.getMe().toUserInfo().also {
            runtimeStorage.updateUserProfile(it)
        }
    }

    override suspend fun getUserInfoFlow(): Flow<UserInfo?> = runtimeStorage.getUserProfileFlow()

    override suspend fun logout() {
        with(sessionManager) {
            clearAccessToken()
            clearRefreshToken()
        }
        clearKtorTokens()
        HttpClientFactory.navigateFlow.tryToUpdate {
            HttpClientFactory.NavigateFlow.LoginFlow
        }
    }

    private fun clearKtorTokens() {
        httpClient
            .authProviders
            .filterIsInstance<BearerAuthProvider>()
            .forEach { it.clearToken() }
    }
}