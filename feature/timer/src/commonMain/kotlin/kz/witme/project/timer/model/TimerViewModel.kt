package kz.witme.project.timer.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.timer.TimerController
import kz.witme.project.timer.TimerUiState

internal class TimerViewModel : ScreenModel, TimerController {

    val uiState: StateFlow<TimerUiState> = MutableStateFlow(TimerUiState())

    private var timerJob: Job? = null
    private var elapsedSeconds: Long = 0L
    private val mutex = Mutex()

    override fun onDispose() {
        super.onDispose()
        stopTimer()
    }

    override fun onTimerClick() {
        if (uiState.value.isRunning) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        if (timerJob?.isActive == true) return
        uiState.tryToUpdate {
            it.copy(
                isRunning = true
            )
        }
        timerJob = screenModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(1000L)
                mutex.withLock {
                    elapsedSeconds++
                    uiState.tryToUpdate {
                        it.copy(
                            timer = TimerHelperModel.getLeftTimerHelperModel(elapsedSeconds)
                        )
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        uiState.tryToUpdate {
            it.copy(
                isRunning = false
            )
        }
    }

    override fun onAddNoteClick() {

    }

    override fun onEndSessionClick() {

    }
}