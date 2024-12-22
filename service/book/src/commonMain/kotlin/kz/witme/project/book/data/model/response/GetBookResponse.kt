package kz.witme.project.book.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.witme.project.book.data.model.ReadingStatusDto
import kz.witme.project.book.data.model.toDomain
import kz.witme.project.book.domain.model.GetBook

@Serializable
internal class GetBookResponse(
    val id: String,
    @SerialName("book_photo") val bookPhoto: String?,
    val name: String,
    val author: String,
    @SerialName("pages_amount") val pagesAmount: Int,
    val description: String,
    @SerialName("reading_status") val readingStatus: String,
    @SerialName("star_rate") val starRate: Double?,
    @SerialName("average_emoji") val avgEmoji: Int? = null,
    @SerialName("notes_amount") val notesAmount: Int?,
    @SerialName("current_page") val currentPage: Int,
    @SerialName("created_date") val createdDate: String? = null
)

internal fun GetBookResponse.toDomain(): GetBook = GetBook(
    id = id,
    bookPhoto = bookPhoto,
    name = name,
    author = author,
    pagesAmount = pagesAmount,
    description = description,
    readingStatus = ReadingStatusDto[readingStatus].toDomain(),
    starRate = starRate,
    avgEmoji = avgEmoji,
    notesAmount = notesAmount,
    currentPage = currentPage,
    createdDate = createdDate
)