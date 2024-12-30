package kz.witme.project.book.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.witme.project.book.domain.model.CreateSessionBody

@Serializable
internal class CreateSessionBodyRequest(
    @SerialName("created_date") val createdDate: String,
    @SerialName("end_session_time") val endSessionTime: String,
    @SerialName("session_duration") val sessionDuration: Int,
    @SerialName("from_page_to_page") val fromPageToPage: String,
    val notes: List<String>,
    @SerialName("current_page") val currentPage: Int
)

internal fun CreateSessionBody.toDto(): CreateSessionBodyRequest = CreateSessionBodyRequest(
    createdDate = createdDate,
    endSessionTime = endSessionTime,
    sessionDuration = sessionDuration,
    fromPageToPage = fromPageToPage,
    notes = notes,
    currentPage = currentPage
)