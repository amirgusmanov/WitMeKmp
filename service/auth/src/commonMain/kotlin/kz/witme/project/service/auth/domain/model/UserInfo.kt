package kz.witme.project.service.auth.domain.model

import kz.witme.project.service.auth.data.model.response.UserInfoResponse

data class UserInfo(
    val username: String?,
    val avatar: String?,
)

internal fun UserInfoResponse.toUserInfo(): UserInfo = UserInfo(
    username = username,
    avatar = avatar
)