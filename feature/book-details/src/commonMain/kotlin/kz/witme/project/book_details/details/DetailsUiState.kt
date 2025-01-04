package kz.witme.project.book_details.details

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.book_details.details.model.SessionItem

@Stable
internal sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Error(val message: String) : DetailsUiState
    data class Data(
        val name: String = "",
        val author: String = "",
        val photo: String? = null,
        val description: String = "",
        val maxPages: Int = 0,
        val details: ImmutableList<SessionItem> = persistentListOf(),
        val errorMessage: String = ""
    ) : DetailsUiState
}