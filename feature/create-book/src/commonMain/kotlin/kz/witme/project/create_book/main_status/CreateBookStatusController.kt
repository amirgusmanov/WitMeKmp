package kz.witme.project.create_book.main_status

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kz.witme.project.book.domain.model.ReadingStatus

@Stable
internal interface CreateBookStatusController {

    val navigateFlow: Flow<CreateBookStatusViewModel.NavigateResult>

    fun onBookStatusSelected(status: ReadingStatus)

    fun onBookDescriptionQueryChanged(query: String)

    fun onStarClick(rating: Int)

    fun onEmojiPicked(emoji: Int)

    fun onEmojiBottomSheetDismiss()

    fun onErrorDismiss()

    fun onNextClick()
}