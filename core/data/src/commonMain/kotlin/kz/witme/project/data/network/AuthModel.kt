package kz.witme.project.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthModel(
    @SerialName("access_token") val access: String,
    @SerialName("refresh_token") val refresh: String?
)

@Serializable
class AuthRequestModel(
    @SerialName("refresh_token") val refresh: String
)