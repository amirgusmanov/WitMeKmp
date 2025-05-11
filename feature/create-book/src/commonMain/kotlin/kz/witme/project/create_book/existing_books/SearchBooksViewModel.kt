package kz.witme.project.create_book.existing_books

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SearchBooksViewModel(

) : ScreenModel {

    val uiState: StateFlow<SearchBooksBooksUiState> = MutableStateFlow(
        SearchBooksBooksUiState(a = 1)
    )

    fun onEvent(event: SearchBooksBooksEvent) {
        when (event) {
            else -> {
                TODO()
            }
        }
    }
}