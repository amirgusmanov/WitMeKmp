package kz.witme.project.timer_details

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.book.domain.model.GetBook

@Stable
internal data class TimerDetailsUiState(
    val bookName: String = "",
    val timerSeconds: Long = 0L,
    val readingStatus: String = "",
    val previousPage: Int = 0,
    val currentPage: Int = 0,
    val maxPages: Int = 0,
    val books: ImmutableList<GetBook> = persistentListOf()
)