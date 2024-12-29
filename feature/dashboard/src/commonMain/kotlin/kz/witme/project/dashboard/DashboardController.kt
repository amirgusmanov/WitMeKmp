package kz.witme.project.dashboard

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
internal interface DashboardController {

    val responseEvent: Flow<DashboardViewModel.DashboardResponseEvent>

    fun onBookClick(bookId: String)

    fun onTimerClick(bookId: String)

    fun onErrorDismiss()
}