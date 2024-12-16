package kz.witme.project.service.auth.data.model.response

import kotlinx.serialization.Serializable

@Serializable
internal class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)