package kz.witme.project.book.data.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kz.witme.project.book.data.model.response.GetBookSessionDetailsResponse

internal interface GetBookDetailsApi {

    @GET("books/{book_id}/details/")
    suspend fun getSessionDetails(
        @Path("book_id") id: String
    ) : List<GetBookSessionDetailsResponse>
}