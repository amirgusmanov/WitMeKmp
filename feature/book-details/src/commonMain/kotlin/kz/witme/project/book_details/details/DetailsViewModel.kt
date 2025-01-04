package kz.witme.project.book_details.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.repository.GetBookSessionDetailsRepository
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.data.network.getMessage
import kz.witme.project.data.network.onError
import kz.witme.project.data.network.onSuccess

internal class DetailsViewModel(
    private val sessionRepository: GetBookSessionDetailsRepository
) : ScreenModel, DetailsController {

    val uiState: StateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState.Loading)

    fun getSessionDetails(book: GetBook) {
        screenModelScope.launch {
            sessionRepository.getBookSessionDetails(book.id)
                .onSuccess { details ->
                    uiState.tryToUpdate {
                        DetailsUiState.Data(
                            details = details,
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
}