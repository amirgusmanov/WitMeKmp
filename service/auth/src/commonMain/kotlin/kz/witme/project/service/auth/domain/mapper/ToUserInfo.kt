package kz.witme.project.service.auth.domain.mapper

import kz.witme.project.profile_runtime_storage.domain.model.UserInfo
import kz.witme.project.service.auth.data.model.response.UserInfoResponse

internal fun UserInfoResponse.toUserInfo(): UserInfo = UserInfo(
    username = username,
    avatar = avatar
)