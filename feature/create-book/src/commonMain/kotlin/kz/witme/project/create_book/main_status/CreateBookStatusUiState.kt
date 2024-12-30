package kz.witme.project.create_book.main_status

import androidx.compose.runtime.Stable
import kz.witme.project.book.domain.model.ReadingStatus

@Stable
internal data class CreateBookStatusUiState(
    val bookDescription: String = "",
    val errorMessage: String = "",
    val selectedBookStatus: ReadingStatus? = null,
    val isCreateButtonLoading: Boolean = false,
    val isPickEmojiBottomSheetVisible: Boolean = false,
    val currentRating: Int = 0,
    val selectedEmoji: Int = -1
)