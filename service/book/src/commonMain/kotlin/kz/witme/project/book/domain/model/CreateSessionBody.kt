package kz.witme.project.book.domain.model

class CreateSessionBody(
    val createdDate: String,
    val endSessionTime: String,
    val sessionDuration: Int,
    val fromPageToPage: String,
    val notes: List<String>,
    val currentPage: Int
)