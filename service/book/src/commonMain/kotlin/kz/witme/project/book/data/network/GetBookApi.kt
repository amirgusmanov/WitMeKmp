package kz.witme.project.book.data.network

import de.jensklingenberg.ktorfit.http.GET
import kz.witme.project.book.data.model.response.GetBookResponse

internal interface GetBookApi {

    @GET("books/list/")
    suspend fun getBooks(): List<GetBookResponse>
}