package kz.witme.project.create_book.existing_books

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle

class SearchBooksScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: SearchBooksViewModel = koinScreenModel()

        SearchBooksScreenContent(
            uiState = viewModel.uiState.collectAsStateWithLifecycle(),
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun SearchBooksScreenContent(
    uiState: State<SearchBooksBooksUiState>,
    onEvent: (SearchBooksBooksEvent) -> Unit
) {

}