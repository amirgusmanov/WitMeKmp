package kz.witme.project.timer

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.timer.model.TimerHelperModel

@Stable
data class TimerUiState(
    val timer: TimerHelperModel = TimerHelperModel.EMPTY,
    val isRunning: Boolean = false,
    val tempNote: String = "",
    val selectedBookId: String = "",
    val books: ImmutableList<GetBook> = persistentListOf(),
    val isTimerAlertVisible: Boolean = false
)