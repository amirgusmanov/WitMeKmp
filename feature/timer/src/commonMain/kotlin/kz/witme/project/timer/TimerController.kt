package kz.witme.project.timer

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
internal interface TimerController {

    val responseEvent: Flow<TimerViewModel.ResponseEvent>

    fun onTimerClick()

    fun onAddNoteClick()

    fun onEndSessionClick()

    fun onBookChoose(bookId: String)
}