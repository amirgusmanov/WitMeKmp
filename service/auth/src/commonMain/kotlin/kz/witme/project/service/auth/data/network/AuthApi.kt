package kz.witme.project.service.auth.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import kz.witme.project.service.auth.data.model.request.AuthRequest
import kz.witme.project.service.auth.data.model.response.LoginResponse
import kz.witme.project.service.auth.data.model.response.UserInfoResponse

internal interface AuthApi {

    @POST("users/login/")
    suspend fun login(
        @Body registerRequest: AuthRequest
    ): LoginResponse

    @POST("users/register/")
    suspend fun register(
        @Body registerRequest: AuthRequest
    ): LoginResponse

    @GET("users/profile/")
    suspend fun getMe(): UserInfoResponse
}