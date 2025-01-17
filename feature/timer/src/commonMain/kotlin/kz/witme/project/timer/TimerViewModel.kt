package kz.witme.project.timer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.book.domain.repository.GetBookRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess
import kz.witme.project.timer.model.TimerHelperModel

internal class TimerViewModel(
    private val booksRepository: GetBookRepository
) : ScreenModel, TimerController {

    val uiState: StateFlow<TimerUiState> = MutableStateFlow(TimerUiState())
    val notesList: MutableList<String> = mutableListOf()

    private val _responseEvent = Channel<ResponseEvent>()
    override val responseEvent = _responseEvent.receiveAsFlow()

    private var timerJob: Job? = null

    private val mutex = Mutex()

    var elapsedSeconds: Long = 0L
        private set

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

    fun restartTimer() {
        timerJob?.cancel()
        timerJob = null
        elapsedSeconds = 0L
        uiState.tryToUpdate {
            it.copy(
                timer = TimerHelperModel.getLeftTimerHelperModel(elapsedSeconds),
                selectedBookId = ""
            )
        }
    }

    fun getBooks() {
        screenModelScope.launch {
            changeBooksLoadingState(true)
            booksRepository.getBooks()
                .onSuccess { books ->
                    uiState.tryToUpdate {
                        it.copy(
                            books = books
                                .filter { book ->
                                    book.readingStatus == ReadingStatus.ReadingNow
                                            || book.readingStatus == ReadingStatus.GoingToRead
                                }
                                .toImmutableList()
                        )
                    }
                }
                .onError {
                    uiState.tryToUpdate {
                        it.copy(areBooksEmpty = true)
                    }
                }
            changeBooksLoadingState(false)
        }
    }

    fun getSelectedBook(id: String): GetBook? = uiState.value.books.firstOrNull { it.id == id }

    fun onNoteChanged(currentNoteText: String) {
        uiState.tryToUpdate {
            it.copy(
                tempNote = currentNoteText
            )
        }
    }

    fun onNoteAdded(note: String) {
        if (note.isBlank()) return
        uiState.tryToUpdate {
            it.copy(
                tempNote = ""
            )
        }
        notesList.add(note.trimIndent())
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

    private fun changeBooksLoadingState(areLoading: Boolean) {
        uiState.tryToUpdate {
            it.copy(areBooksLoading = areLoading)
        }
    }

    override fun onAddNoteClick() {
        screenModelScope.launch {
            _responseEvent.send(ResponseEvent.ShowBookNoteSheet)
        }
    }

    override fun onEndSessionClick() {
        screenModelScope.launch {
            stopTimer()
            _responseEvent.send(ResponseEvent.NavigateToDetails)
        }
    }

    override fun onBookChoose(bookId: String) {
        screenModelScope.launch {
            uiState.tryToUpdate {
                it.copy(
                    selectedBookId = bookId
                )
            }
            _responseEvent.send(ResponseEvent.NavigateToDetails)
        }
    }

    internal sealed interface ResponseEvent {
        data object NavigateToDetails : ResponseEvent
        data object ShowBookNoteSheet : ResponseEvent
    }
}