package kz.witme.project.book.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.request.forms.MultiPartFormDataContent
import kz.witme.project.book.data.model.response.CreateBookResponse

interface CreateBookApi {

    @Multipart
    @POST("books/create/")
    suspend fun createBook(
        @Body map: MultiPartFormDataContent
    ): CreateBookResponse
}