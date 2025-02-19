package kz.witme.project.book_details.details_session

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.common.extension.tryToUpdate

internal class SessionDetailsInfoViewModel(
    private val session: GetBookSessionDetails,
    private val bookName: String
) : ScreenModel {

    val uiState: StateFlow<SessionDetailsInfoUiState> = MutableStateFlow(
        SessionDetailsInfoUiState()
    )

    init {
        initUiState()
    }

    private fun initUiState() {
        uiState.tryToUpdate {
            it.copy(
                bookName = bookName,
                date = session.createdDate,
                elapsedTime = session.getMinutes(),
                notes = session.notes.toImmutableList()
            )
        }
    }
}