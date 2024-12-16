package kz.witme.project.profile.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kz.witme.project.profile.data.network.ProfileUpdateApi
import kz.witme.project.profile.domain.repository.ProfileUpdateRepository
import kotlin.random.Random

internal class ProfileUpdateRepositoryImpl(
    private val api: ProfileUpdateApi
) : ProfileUpdateRepository {

    override suspend fun uploadAvatar(
        avatar: ByteArray,
        username: String
    ): RequestResult<Unit, DataError.Remote> = safeCall {
        api.updateProfile(
            map = MultiPartFormDataContent(
                formData {
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
            )
        )
    }
}