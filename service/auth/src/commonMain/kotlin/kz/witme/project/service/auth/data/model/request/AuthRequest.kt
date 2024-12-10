package kz.witme.project.service.auth.data.model.request

import kotlinx.serialization.Serializable

@Serializable
internal class AuthRequest(
    val email: String,
    val password: String
)