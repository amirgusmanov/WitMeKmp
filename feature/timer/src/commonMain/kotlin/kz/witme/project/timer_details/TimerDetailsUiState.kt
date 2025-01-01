package kz.witme.project.timer_details

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.book.domain.model.GetBook

@Stable
internal data class TimerDetailsUiState(
    val book: GetBook? = null,
    val timerSeconds: Long = 0L,
    val maxPages: Int = 0,
    val previousPage: Int = 0,
    val currentPage: Int = 0,
    val notes: ImmutableList<String> = persistentListOf(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)