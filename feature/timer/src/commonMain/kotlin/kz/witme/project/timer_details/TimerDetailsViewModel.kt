package kz.witme.project.timer_details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.repository.CreateBookSessionRepository
import kz.witme.project.common.extension.tryToGet
import kz.witme.project.common.extension.tryToUpdate

internal class TimerDetailsViewModel(
    private val createBookSessionRepository: CreateBookSessionRepository
) : ScreenModel, TimerDetailsController {

    val uiState: StateFlow<TimerDetailsUiState> = MutableStateFlow(TimerDetailsUiState())

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent: Flow<ResponseEvent> = _responseEvent.receiveAsFlow()

    fun initState(
        bookName: String,
        seconds: Long,
        readingStatus: String,
        previousPage: Int,
        maxPages: Int
    ) {
        uiState.tryToUpdate {
            it.copy(
                bookName = bookName,
                timerSeconds = seconds,
                readingStatus = readingStatus,
                previousPage = previousPage,
                maxPages = maxPages
            )
        }
    }

    override fun onConfirmClick() {
        screenModelScope.launch {
            TODO("make network request here")
        }
    }

    //todo implement later
    override fun onTimerClick() = Unit

    override fun onPageSheetDismiss() {
        screenModelScope.launch {
            _responseEvent.send(ResponseEvent.HidePagePicker)
        }
    }

    override fun onPagesCountClick() {
        screenModelScope.launch {
            _responseEvent.send(ResponseEvent.ShowPagePicker)
        }
    }

    override fun onCurrentPageSelect(page: String) {
        screenModelScope.launch {
            uiState.tryToUpdate {
                it.copy(
                    currentPage = tryToGet {
                        page
                            .substringBefore(" ")
                            .trimIndent()
                            .toInt()
                    } ?: 0
                )
            }
            _responseEvent.send(ResponseEvent.HidePagePicker)
        }
    }

    internal sealed interface ResponseEvent {
        data object ShowPagePicker : ResponseEvent
        data object HidePagePicker : ResponseEvent
        data object NavigateToDashboard : ResponseEvent
    }
}