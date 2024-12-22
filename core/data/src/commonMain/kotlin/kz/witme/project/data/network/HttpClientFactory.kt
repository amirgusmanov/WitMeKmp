package kz.witme.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kz.witme.project.data.local.SessionManager
import kz.witme.project.data.util.Constants
import kz.witme.project.common.log.Logger as SharedLogger

internal object HttpClientFactory {

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
                            //todo need to navigate to onboarding
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
        onRefreshFailed: suspend () -> Unit
    ): BearerTokens {
        val response = client.post(Url(Constants.REFRESH_URL)) {
            markAsRefreshTokenRequest()
            contentType(ContentType.Application.Json)
            setBody(
                AuthModel(
                    access = sessionManager.getAccessToken(),
                    refresh = sessionManager.getRefreshToken()
                )
            )
        }
        if (response.status.value == 401) {
            sessionManager.clearAccessToken()
            sessionManager.clearRefreshToken()
            onRefreshFailed()
        } else {
            with(response.body<AuthModel>()) {
                sessionManager.setAccessToken(access)
                sessionManager.setRefreshToken(refresh ?: return@with)
            }
        }
        return with(response.body<AuthModel>()) {
            BearerTokens(accessToken = access, refreshToken = refresh)
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
            requestTimeoutMillis = 20_000L
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
}