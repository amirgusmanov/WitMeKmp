package kz.witme.project.book_details.details

import androidx.compose.runtime.Stable
import kz.witme.project.book.domain.model.GetBookSessionDetails

@Stable
sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Data(
        val name: String = "",
        val author: String = "",
        val photo: String = "",
        val description: String = "",
        val maxPages: Int = 0,
        val details: GetBookSessionDetails? = null
    ) : DetailsUiState
}