package kz.witme.project.profile.data.network

import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Part
import io.ktor.http.content.PartData

internal interface ProfileApi {

    @Multipart
    @POST("/users/update_profile/")
    suspend fun updateProfile(
        @Part("username") username: String,
        @Part("avatar") avatar: PartData
    )
}