package kz.witme.project.create_book.main_status

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.create_book.main.CreateBookMainUiState

internal class CreateBookStatusViewModel(
    private val repository: CreateBookRepository
) : ScreenModel, CreateBookStatusController {

    val uiState: StateFlow<CreateBookStatusUiState> = MutableStateFlow(CreateBookStatusUiState())

    private val _navigateChannel = Channel<NavigateResult>()
    override val navigateFlow: Flow<NavigateResult> = _navigateChannel.receiveAsFlow()

    private val createBookMainUiState: CreateBookMainUiState? = null

    private suspend fun createBook() {
        screenModelScope.launch {
            repository.createBook(
                with(uiState.value) {
//                    CreateBookRequest(
//                        name = ,
//                        author =,
//                        pagesAmount =,
//                        description =,
//                        readingStatus =,
//                        starRate =,
//                        averageEmoji =,
//                        currentPage =,
//                        image =
//                    )
                    TODO("Send request here")
                }
            )
        }
    }

    override fun onBookStatusSelected(status: ReadingStatus) {
        uiState.tryToUpdate {
            it.copy(
                selectedBookStatus = status
            )
        }
    }

    override fun onBookDescriptionQueryChanged(query: String) {
        uiState.tryToUpdate {
            it.copy(
                bookDescription = query
            )
        }
    }

    override fun onStarClick(rating: Int) {
        uiState.tryToUpdate {
            it.copy(
                currentRating = rating
            )
        }
    }

    override fun onEmojiPicked(emoji: Int) {
        uiState.tryToUpdate {
            it.copy(
                selectedEmoji = emoji
            )
        }
    }

    override fun onEmojiBottomSheetDismiss() {
        uiState.tryToUpdate {
            it.copy(
                isPickEmojiBottomSheetVisible = false
            )
        }
    }

    override fun onErrorDismiss() {
        uiState.tryToUpdate {
            it.copy(
                errorMessage = ""
            )
        }
    }

    override fun onNextClick() {
        screenModelScope.launch {
            when (uiState.value.selectedBookStatus) {
                ReadingStatus.GoingToRead,
                ReadingStatus.ReadingNow -> createBook()

                ReadingStatus.FinishedReading -> {
                    uiState.tryToUpdate {
                        it.copy(
                            isPickEmojiBottomSheetVisible = true
                        )
                    }
                }

                null -> Unit
            }
        }
    }

    internal sealed interface NavigateResult {
        data object NavigateToDashboard : NavigateResult
    }
}