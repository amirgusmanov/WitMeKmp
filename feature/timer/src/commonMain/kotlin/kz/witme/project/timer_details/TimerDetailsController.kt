package kz.witme.project.timer_details

import androidx.compose.runtime.Stable

@Stable
internal interface TimerDetailsController {

    fun onConfirmClick()

    fun onTimerClick()

    fun onPageSheetDismiss()

    fun onPagesCountClick()

    fun onCurrentPageSelect(page: String)
}