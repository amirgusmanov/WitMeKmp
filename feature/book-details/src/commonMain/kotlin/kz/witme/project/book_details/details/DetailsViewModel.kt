package kz.witme.project.book_details.details

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.witme.project.book.domain.repository.GetBookSessionDetailsRepository

internal class DetailsViewModel(
    private val sessionRepository: GetBookSessionDetailsRepository
) : ScreenModel, DetailsController {

    val uiState: StateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState.Loading)
}