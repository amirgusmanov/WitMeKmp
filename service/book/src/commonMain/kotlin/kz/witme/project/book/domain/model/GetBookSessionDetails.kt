package kz.witme.project.book.domain.model

import kz.witme.project.common.serializable.Serializable

data class GetBookSessionDetails(
    val createdDate: String,
    val endSessionTime: String,
    val sessionDuration: Int,
    val fromPageToPage: String,
    val notes: List<String>,
    val currentPage: Int
) : Serializable {
    fun getMinutes(): Int {
        val minutes = (sessionDuration % 3600) / 60
        return if (minutes < 1) 1 else minutes
    }
}