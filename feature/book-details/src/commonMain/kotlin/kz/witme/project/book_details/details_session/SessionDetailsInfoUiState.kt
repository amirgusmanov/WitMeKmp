package kz.witme.project.book_details.details_session

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class SessionDetailsInfoUiState(
    val bookName: String = "",
    val date: String = "",
    val elapsedTime: Int = 0,
    val notes: ImmutableList<String> = persistentListOf()
)