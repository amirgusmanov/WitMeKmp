package kz.witme.project.dashboard

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.book.domain.model.GetBook

@Stable
internal data class DashboardUiState(
    val currentlyReadingBooks: ImmutableList<BookEntry> = persistentListOf(),
    val toReadBooks: ImmutableList<BookEntry> = persistentListOf(),
    val finishedReadingBooks: ImmutableList<GetBook> = persistentListOf(),
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val isBookCreated: Boolean = false
)