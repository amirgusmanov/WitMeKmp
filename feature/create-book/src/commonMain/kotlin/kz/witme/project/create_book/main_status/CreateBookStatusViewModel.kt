package kz.witme.project.create_book.main_status

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.model.CreateBookRequest
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess
import kz.witme.project.navigation.CreateBookArgs

internal class CreateBookStatusViewModel(
    private val repository: CreateBookRepository
) : ScreenModel, CreateBookStatusController {

    val uiState: StateFlow<CreateBookStatusUiState> = MutableStateFlow(CreateBookStatusUiState())

    private val _navigateChannel = Channel<NavigateResult>()
    override val navigateFlow: Flow<NavigateResult> = _navigateChannel.receiveAsFlow()

    private var navArgs: CreateBookArgs? = null

    private suspend fun createBook() {
        screenModelScope.launch {
            uiState.tryToUpdate {
                it.copy(
                    isCreateButtonLoading = true
                )
            }
            repository.createBook(
                navArgs?.let { args ->
                    with(uiState.value) {
                        CreateBookRequest(
                            name = args.bookName,
                            author = args.authorName,
                            pagesAmount = args.bookListCount.toString(),
                            description = bookDescription,
                            readingStatus = selectedBookStatus ?: ReadingStatus.ReadingNow,
                            starRate = currentRating,
                            averageEmoji = selectedEmoji,
                            image = args.imageByteArray,
                            currentPage = 0
                        )
                    }
                } ?: return@launch
            ).onSuccess {
                _navigateChannel.send(NavigateResult.NavigateToDashboard)
            }.onError { error ->
                uiState.tryToUpdate {
                    it.copy(errorMessage = error.getMessage())
                }
            }
            uiState.tryToUpdate {
                it.copy(
                    isCreateButtonLoading = false
                )
            }
        }
    }

    fun onLaunched(args: CreateBookArgs) {
        navArgs = args
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
        screenModelScope.launch {
            uiState.tryToUpdate {
                it.copy(
                    selectedEmoji = emoji,
                    isPickEmojiBottomSheetVisible = false
                )
            }
            createBook()
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