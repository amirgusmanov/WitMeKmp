package kz.witme.project.service.auth.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import kz.witme.project.service.auth.data.model.request.AuthRequest
import kz.witme.project.service.auth.data.model.response.LoginResponse
import kz.witme.project.service.auth.data.model.response.UserInfoResponse

internal interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body registerRequest: AuthRequest
    ): Result<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: AuthRequest
    ): Result<LoginResponse>

    @GET("auth/me")
    suspend fun getMe(): Result<UserInfoResponse>
}