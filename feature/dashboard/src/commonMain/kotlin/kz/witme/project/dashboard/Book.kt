package kz.witme.project.dashboard

data class Book(
    val name: String,
    val author: String,
    val totalDuration: Int,
    val description: String,
    val bookStatus: BookStatus,
    val imageUri: String,
    val planningDate: String?,
    val endDate: String?,
    val startDate: String?,
    val rating: Int?,
    val rateDescription: String?,
    val avgEmoji: Int?
)