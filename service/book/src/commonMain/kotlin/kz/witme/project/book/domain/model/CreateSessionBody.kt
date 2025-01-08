package kz.witme.project.book.domain.model

class CreateSessionBody(
    val sessionDuration: Int,
    val fromPageToPage: String,
    val notes: List<String>,
    val currentPage: Int,
    val fromTimeToTime: String
)