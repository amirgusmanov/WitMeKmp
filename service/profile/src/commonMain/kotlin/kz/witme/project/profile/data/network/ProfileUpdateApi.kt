package kz.witme.project.profile.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.request.forms.MultiPartFormDataContent
import kz.witme.project.profile.data.model.UserProfileResponse

internal interface ProfileUpdateApi {

    @Multipart
    @POST("/users/update_profile/")
    suspend fun updateProfile(
        @Body map: MultiPartFormDataContent
    ): UserProfileResponse

    @DELETE("users/delete/")
    suspend fun deleteAccount()
}