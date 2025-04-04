package kz.witme.project.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.book.domain.repository.GetBookRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError

internal class DashboardViewModel(
    private val bookRepository: GetBookRepository
) : ScreenModel, DashboardController {

    val uiState: StateFlow<DashboardUiState> = MutableStateFlow(DashboardUiState())

    private val _responseEvent = Channel<DashboardResponseEvent>()
    override val responseEvent: Flow<DashboardResponseEvent> = _responseEvent.receiveAsFlow()

    init {
        updateBooks()
        observeBooks()
    }

    fun showSuccessDialog() {
        uiState.tryToUpdate {
            it.copy(
                isBookCreated = true
            )
        }
    }

    fun dismissSuccessDialog() {
        uiState.tryToUpdate {
            it.copy(
                isBookCreated = false
            )
        }
    }

    fun updateBooks() {
        screenModelScope.launch {
            initLoading()
            bookRepository.updateBooks().onError { error ->
                uiState.tryToUpdate {
                    it.copy(errorMessage = error.getMessage())
                }
            }
            endLoading()
        }
    }

    private fun observeBooks() {
        screenModelScope.launch {
            bookRepository.observeBooks().collectLatest {
                filterBooks(it)
            }
        }
    }

    private fun filterBooks(bookList: List<GetBook>) {
        uiState.tryToUpdate {
            it.copy(
                currentlyReadingBooks = bookList
                    .filter { book -> book.readingStatus == ReadingStatus.ReadingNow }
                    .toCurrentlyReadingBookEntries(),
                toReadBooks = bookList
                    .filter { book -> book.readingStatus == ReadingStatus.GoingToRead }
                    .toPlanningToReadBookEntries(),
                finishedReadingBooks = bookList
                    .filter { book -> book.readingStatus == ReadingStatus.FinishedReading }
                    .toImmutableList()
            )
        }
    }

    private fun initLoading() {
        uiState.tryToUpdate {
            it.copy(isLoading = true)
        }
    }

    private fun endLoading() {
        uiState.tryToUpdate {
            it.copy(isLoading = false)
        }
    }

    override fun onErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(errorMessage = "")
        }
    }

    override fun onBookClick(book: GetBook) {
        screenModelScope.launch {
            _responseEvent.send(DashboardResponseEvent.NavigateToDetails(book))
        }
    }

    override fun onTimerClick(bookId: String) {
        screenModelScope.launch {
            _responseEvent.send(DashboardResponseEvent.NavigateToTimer(bookId))
        }
    }

    override fun onEmptyClick() {
        screenModelScope.launch {
            _responseEvent.send(DashboardResponseEvent.NavigateToCreateBook)
        }
    }

    sealed interface DashboardResponseEvent {
        data class NavigateToTimer(val bookId: String) : DashboardResponseEvent
        data class NavigateToDetails(val book: GetBook) : DashboardResponseEvent
        data object NavigateToCreateBook : DashboardResponseEvent
    }
}