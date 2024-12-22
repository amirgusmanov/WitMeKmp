package kz.witme.project.dashboard

import androidx.compose.runtime.Stable

@Stable
internal interface DashboardController {

    fun onBookClick(bookId: String)

    fun onTimerClick(bookId: String)

    fun onErrorDismiss()
}