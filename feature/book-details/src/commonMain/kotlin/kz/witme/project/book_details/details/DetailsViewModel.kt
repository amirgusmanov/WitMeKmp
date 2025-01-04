package kz.witme.project.book_details.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.book.domain.repository.GetBookSessionDetailsRepository
import kz.witme.project.book_details.details.model.SessionItem
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess

internal class DetailsViewModel(
    private val sessionRepository: GetBookSessionDetailsRepository
) : ScreenModel, DetailsController {

    val uiState: StateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState.Loading)

    private val _responseEventFlow = Channel<ResponseEvent>()
    val responseEventFlow: Flow<ResponseEvent> = _responseEventFlow.receiveAsFlow()

    fun getSessionDetails(book: GetBook) {
        screenModelScope.launch {
            sessionRepository.getBookSessionDetails(book.id)
                .onSuccess { details ->
                    uiState.tryToUpdate {
                        DetailsUiState.Data(
                            details = details.filterSessionDetails(),
                            name = book.name,
                            author = book.author,
                            photo = book.bookPhoto,
                            description = book.description,
                            maxPages = book.pagesAmount
                        )
                    }
                }
                .onError { error ->
                    uiState.tryToUpdate {
                        DetailsUiState.Error(
                            message = error.getMessage()
                        )
                    }
                }
        }
    }

    private fun List<GetBookSessionDetails>.filterSessionDetails(): ImmutableList<SessionItem> =
        this
            .groupBy { it.createdDate }
            .entries
            .sortedBy { it.key }
            .flatMapIndexed { index, (date, sessions) ->
                val sessionItems = sessions.map { SessionItem.BookSessionDetails(it) }
                listOf(SessionItem.Date(day = index + 1, date = date)) + sessionItems
            }
            .toImmutableList()

    override fun onSessionClick(session: GetBookSessionDetails) {
        TODO("Not yet implemented")
    }

    override fun onErrorDismiss() {
        screenModelScope.launch {
            _responseEventFlow.send(ResponseEvent.NavigateBack)
        }
    }

    sealed interface ResponseEvent {
        data object NavigateBack : ResponseEvent
        data class NavigateToSessionDetails(
            val session: GetBookSessionDetails
        ) : ResponseEvent
    }
}