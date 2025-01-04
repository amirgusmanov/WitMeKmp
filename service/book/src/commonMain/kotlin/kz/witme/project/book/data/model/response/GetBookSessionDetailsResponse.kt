package kz.witme.project.book.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.witme.project.book.domain.model.GetBookSessionDetails

@Serializable
internal class GetBookSessionDetailsResponse(
    @SerialName("created_date") val createdDate: String,
    @SerialName("end_session_time") val endSessionTime: String?,
    @SerialName("session_duration") val sessionDuration: Int,
    @SerialName("from_page_to_page") val fromPageToPage: String,
    val notes: List<String>,
    @SerialName("current_page") val currentPage: Int
)

internal fun GetBookSessionDetailsResponse.toDomain(): GetBookSessionDetails = GetBookSessionDetails(
    createdDate = createdDate,
    endSessionTime = endSessionTime.orEmpty(),
    sessionDuration = sessionDuration,
    fromPageToPage = fromPageToPage,
    notes = notes,
    currentPage = currentPage
)