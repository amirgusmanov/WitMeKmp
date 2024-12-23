package kz.witme.project.book.domain.model

class CreateBookRequest(
    val name: String,
    val author: String,
    val pagesAmount: String,
    val description: String,
    val readingStatus: ReadingStatus,
    val starRate: Int?,
    val averageEmoji: Int?,
    val currentPage: Int?,
    val image: ByteArray?
)