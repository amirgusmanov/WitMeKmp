package kz.witme.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.local.SessionManager
import kz.witme.project.data.util.Constants
import kz.witme.project.common.log.Logger as SharedLogger

object HttpClientFactory {

    //todo put it somewhere else
    val navigateFlow: StateFlow<NavigateFlow> = MutableStateFlow(NavigateFlow.SplashFlow)

    fun create(
        engine: HttpClientEngine,
        sessionManager: SessionManager
    ): HttpClient = HttpClient(engine) {
        installContentNegotiation()
        installHttpTimeout()
        installLogging()
        installAuth(sessionManager)
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    private fun HttpClientConfig<*>.installAuth(sessionManager: SessionManager) {
        install(Auth) {
            bearer {
                refreshTokens {
                    sendRefreshRequest(
                        sessionManager = sessionManager,
                        onRefreshFailed = {
                            navigateFlow.tryToUpdate { NavigateFlow.LoginFlow }
                        },
                        onRefreshSuccess = {
                            navigateFlow.tryToUpdate { NavigateFlow.TabsFlow }
                        }
                    )
                }
                loadTokens {
                    BearerTokens(
                        accessToken = sessionManager.getAccessToken(),
                        refreshToken = sessionManager.getRefreshToken()
                    )
                }
                sendWithoutRequest { request ->
                    listOf(
                        Constants.LOGIN_URL,
                        Constants.REG_URL
                    ).contains(request.url.toString()).not()
                }
            }
        }
    }

    private suspend fun RefreshTokensParams.sendRefreshRequest(
        sessionManager: SessionManager,
        onRefreshFailed: suspend () -> Unit,
        onRefreshSuccess: suspend () -> Unit
    ): BearerTokens {
        val response = client.post(Url(Constants.REFRESH_URL)) {
            markAsRefreshTokenRequest()
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer ${sessionManager.getAccessToken()}")
            setBody(
                AuthRequestModel(refresh = sessionManager.getRefreshToken())
            )
        }
        when (response.status.value) {
            401 -> {
                this.client.authProviders
                    .filterIsInstance<BearerAuthProvider>()
                    .forEach {
                        it.clearToken()
                    }
                onRefreshFailed()
                throw IllegalStateException("Failed to refresh token: Unauthorized")
            }

            in 200..299 -> {
                val authModel = response.body<AuthModel>()
                sessionManager.setAccessToken(authModel.access)
                sessionManager.setRefreshToken(
                    authModel.refresh ?: throw IllegalStateException("Refresh token is null")
                )
                this.client.authProviders
                    .filterIsInstance<BearerAuthProvider>()
                    .forEach {
                        it.clearToken()
                    }
                onRefreshSuccess()
                return BearerTokens(
                    accessToken = authModel.access,
                    refreshToken = authModel.refresh
                )
            }

            else -> {
                throw IllegalStateException(
                    "Failed to refresh token: Unexpected status ${response.status.value}"
                )
            }
        }
    }

    private fun HttpClientConfig<*>.installContentNegotiation() {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    private fun HttpClientConfig<*>.installHttpTimeout() {
        install(HttpTimeout) {
            socketTimeoutMillis = 20_000L
            requestTimeoutMillis = 30_000L
        }
    }

    private fun HttpClientConfig<*>.installLogging() {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    SharedLogger.d(Constants.NETWORK_LOG_TAG, message)
                }
            }
            level = LogLevel.ALL
        }
    }

    enum class NavigateFlow {
        LoginFlow,
        TabsFlow,
        SplashFlow
    }
}