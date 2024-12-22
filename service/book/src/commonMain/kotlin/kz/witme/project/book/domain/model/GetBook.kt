package kz.witme.project.book.domain.model

data class GetBook(
    val id: String,
    val bookPhoto: String?,
    val name: String,
    val author: String,
    val pagesAmount: Int,
    val description: String,
    val readingStatus: ReadingStatus,
    val starRate: Double?,
    val avgEmoji: Int? = null,
    val notesAmount: Int?,
    val currentPage: Int,
    val createdDate: String? = null
)