package kz.witme.project.profile.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kz.witme.project.profile.data.model.toUserInfo
import kz.witme.project.profile.data.network.ProfileUpdateApi
import kz.witme.project.profile.domain.repository.ProfileUpdateRepository
import kz.witme.project.profile_runtime_storage.data.local.UserProfileRuntimeStorage
import kotlin.random.Random

internal class ProfileUpdateRepositoryImpl(
    private val api: ProfileUpdateApi,
    private val profileRuntimeStorage: UserProfileRuntimeStorage
) : ProfileUpdateRepository {

    override suspend fun uploadAvatar(
        avatar: ByteArray,
        username: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        api.updateProfile(
            map = MultiPartFormDataContent(
                getAvatarFormData(username = username, avatar = avatar)
            )
        ).toUserInfo().also {
            profileRuntimeStorage.updateUserProfile(it)
        }
    }

    override suspend fun deleteAccount(): RequestResult<Unit, DataError.Remote> = safeCall {
        api.deleteAccount()
    }

    private fun getAvatarFormData(
        username: String,
        avatar: ByteArray
    ) = formData {
        append("username", username)
        append(
            key = "avatar",
            value = avatar,
            headers = Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(
                    HttpHeaders.ContentDisposition,
                    "form-data; filename=\"avatar${avatar.size + Random.nextInt()}.jpg\""
                )
            }
        )
    }
}