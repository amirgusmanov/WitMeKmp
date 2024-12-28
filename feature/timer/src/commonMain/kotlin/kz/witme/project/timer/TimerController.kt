package kz.witme.project.timer

import androidx.compose.runtime.Stable

@Stable
internal interface TimerController {

    fun onTimerClick()

    fun onAddNoteClick()

    fun onEndSessionClick()
}