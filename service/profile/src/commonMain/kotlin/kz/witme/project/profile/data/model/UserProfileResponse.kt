package kz.witme.project.profile.data.model

import kotlinx.serialization.Serializable

@Serializable
class UserProfileResponse(
    val avatar: String,
    val username: String
)