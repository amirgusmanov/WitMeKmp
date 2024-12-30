package kz.witme.project.book.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import kz.witme.project.book.data.model.request.CreateSessionBodyRequest
import kz.witme.project.book.data.model.response.DefaultResponse

internal interface CreateBookSessionApi {

    @POST("/books/{book_id}/create_session/")
    suspend fun createSession(
        @Path("book_id") id: String,
        @Body body: CreateSessionBodyRequest
    ): DefaultResponse
}