package kz.witme.project.book.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kz.witme.project.book.data.network.CreateBookApi
import kz.witme.project.book.domain.model.CreateBookRequest
import kz.witme.project.book.domain.model.toDto
import kz.witme.project.book.domain.repository.CreateBookRepository
import kotlin.random.Random

internal class CreateBookRepositoryImpl(
    private val api: CreateBookApi
) : CreateBookRepository {

    override suspend fun createBook(request: CreateBookRequest) {
        api.createBook(
            map = MultiPartFormDataContent(
                formData {
                    append("name", request.name)
                    append("author", request.author)
                    append("pages_amount", request.pagesAmount)
                    append("description", request.description)
                    append("reading_status", request.readingStatus.toDto().backendValue)
                    request.starRate?.let { append("star_rate", it) }
                    request.averageEmoji?.let { append("average_emotion", it) }
                    request.currentPage?.let { append("current_page", it) }
                    request.image?.let {
                        append(
                            key = "book_photo",
                            value = it,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; filename=\"avatar${request.image.size + Random.nextInt()}.jpg\""
                                )
                            }
                        )
                    }
                }
            )
        )
    }
}