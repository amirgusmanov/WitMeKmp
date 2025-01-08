package kz.witme.project.book.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.witme.project.book.domain.model.CreateSessionBody

@Serializable
internal class CreateSessionBodyRequest(
    @SerialName("session_duration") val sessionDuration: Int,
    @SerialName("from_page_to_page") val fromPageToPage: String,
    val notes: List<String>,
    @SerialName("current_page") val currentPage: Int,
    @SerialName("from_time_to_time") val fromTimeToTime: String,
)

internal fun CreateSessionBody.toDto(): CreateSessionBodyRequest = CreateSessionBodyRequest(
    sessionDuration = sessionDuration,
    fromPageToPage = fromPageToPage,
    notes = notes,
    currentPage = currentPage,
    fromTimeToTime = fromTimeToTime
)