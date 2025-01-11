package kz.witme.project.book.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.witme.project.book.data.network.CreateBookApi
import kz.witme.project.book.domain.model.CreateBookRequest
import kz.witme.project.book.domain.model.toDto
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.data.network.safeCall
import kotlin.random.Random

internal class CreateBookRepositoryImpl(
    private val api: CreateBookApi
) : CreateBookRepository {

    private val tempImageFlow: StateFlow<ByteArray?> = MutableStateFlow(null)

    override suspend fun createBook(
        request: CreateBookRequest
    ): RequestResult<Unit, DataError.Remote> = safeCall {
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
                    tempImageFlow.value?.let { byteArray ->
                        append(
                            key = "book_photo",
                            value = byteArray,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; filename=\"avatar${byteArray.size + Random.nextInt()}.jpg\""
                                )
                            }
                        )
                    }
                }
            )
        )
    }

    override fun saveTempImage(image: ByteArray?) {
        tempImageFlow.tryToUpdate {
            image
        }
    }

    override fun clearTempImage() {
        tempImageFlow.tryToUpdate {
            null
        }
    }
}