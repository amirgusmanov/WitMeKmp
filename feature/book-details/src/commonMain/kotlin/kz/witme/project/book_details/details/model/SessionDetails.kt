package kz.witme.project.book_details.details.model

import kz.witme.project.book.domain.model.GetBookSessionDetails

sealed interface SessionItem {
    data class BookSessionDetails(val details: GetBookSessionDetails) : SessionItem
    data class Date(
        val day: Int,
        val date: String
    ) : SessionItem
}