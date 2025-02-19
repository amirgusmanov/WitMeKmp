package kz.witme.project.timer_details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kz.witme.project.book.domain.model.CreateSessionBody
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.repository.CreateBookSessionRepository
import kz.witme.project.common.extension.tryToGet
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess

internal class TimerDetailsViewModel(
    private val createBookSessionRepository: CreateBookSessionRepository,
    private val book: GetBook,
    private val elapsedSeconds: Long,
    private val notes: ImmutableList<String>,
    private val isFromTabs: Boolean
) : ScreenModel, TimerDetailsController {

    val uiState: StateFlow<TimerDetailsUiState> = MutableStateFlow(TimerDetailsUiState())

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent: Flow<ResponseEvent> = _responseEvent.receiveAsFlow()

    init {
        initState()
        screenModelScope.launch {
            if (isFromTabs) {
                /**
                 * костыль :) походу bottomShitNavigator не успевает среагировать
                 * на новый ивент открытия другого боттомщита когда навигатор tabs
                 */
                delay(1000L)
            }
            _responseEvent.send(ResponseEvent.ShowPagePicker)
        }
    }

    private fun initState() {
        uiState.tryToUpdate {
            it.copy(
                book = book,
                timerSeconds = elapsedSeconds,
                previousPage = book.currentPage + 1,
                currentPage = book.currentPage + 1,
                maxPages = book.pagesAmount,
                notes = notes
            )
        }
    }

    override fun onConfirmClick() {
        screenModelScope.launch {
            with(uiState.value) {
                uiState.tryToUpdate {
                    it.copy(
                        isLoading = true
                    )
                }
                createBookSessionRepository.createSession(
                    id = book?.id ?: return@launch,
                    body = CreateSessionBody(
                        sessionDuration = timerSeconds.toInt(),
                        fromPageToPage = "$previousPage-$currentPage",
                        notes = notes,
                        currentPage = currentPage,
                        fromTimeToTime = getTime()
                    )
                ).onSuccess {
                    _responseEvent.send(ResponseEvent.NavigateToDashboard)
                }.onError { error ->
                    uiState.tryToUpdate {
                        it.copy(
                            errorMessage = error.getMessage()
                        )
                    }
                }
                uiState.tryToUpdate {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(
                errorMessage = ""
            )
        }
    }

    private fun getTime(): String {
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val currentDateTime = now.toLocalDateTime(timeZone)
        val currentTime = currentDateTime.time
        val timerSeconds = uiState.value.timerSeconds
        val totalCurrentSeconds =
            currentTime.hour * 3600 + currentTime.minute * 60 + currentTime.second
        val totalStartSeconds = totalCurrentSeconds - timerSeconds

        // Handle negative time wrap-around (e.g., crossing midnight)
        val normalizedStartSeconds = if (totalStartSeconds >= 0) {
            totalStartSeconds
        } else {
            24 * 3600 + totalStartSeconds
        }

        val startHours = normalizedStartSeconds / 3600
        val startMinutes = (normalizedStartSeconds % 3600) / 60
        val startFormatted = buildString {
            append(startHours.toString().padStart(2, '0'))
            append(":")
            append(startMinutes.toString().padStart(2, '0'))
        }
        val endFormatted = buildString {
            append(currentTime.hour.toString().padStart(2, '0'))
            append(":")
            append(currentTime.minute.toString().padStart(2, '0'))
        }

        return "$startFormatted-$endFormatted"
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