package kz.witme.project.timer

import androidx.compose.runtime.Stable
import kz.witme.project.timer.model.TimerHelperModel

@Stable
data class TimerUiState(
    val timer: TimerHelperModel = TimerHelperModel.EMPTY,
    val isRunning: Boolean = false
)