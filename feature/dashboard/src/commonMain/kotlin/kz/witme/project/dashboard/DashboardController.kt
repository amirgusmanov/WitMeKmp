package kz.witme.project.dashboard

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kz.witme.project.book.domain.model.GetBook

@Stable
internal interface DashboardController {

    val responseEvent: Flow<DashboardViewModel.DashboardResponseEvent>

    fun onBookClick(book: GetBook)

    fun onTimerClick(bookId: String)

    fun onEmptyClick()

    fun onErrorDismiss()
}