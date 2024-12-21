package kz.witme.project.service.auth.data.model.response

import kotlinx.serialization.Serializable

@Serializable
internal class UserInfoResponse(
    val username: String?,
    val avatar: String?
)