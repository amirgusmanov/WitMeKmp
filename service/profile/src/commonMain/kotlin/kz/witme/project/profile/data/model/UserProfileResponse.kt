package kz.witme.project.profile.data.model

import kotlinx.serialization.Serializable
import kz.witme.project.profile_runtime_storage.domain.model.UserInfo

@Serializable
internal class UserProfileResponse(
    val avatar: String,
    val username: String
)

internal fun UserProfileResponse.toUserInfo(): UserInfo = UserInfo(
    avatar = avatar,
    username = username
)