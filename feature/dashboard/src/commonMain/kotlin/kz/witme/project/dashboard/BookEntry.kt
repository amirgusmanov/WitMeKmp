package kz.witme.project.dashboard

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kz.witme.project.book.domain.model.GetBook

@Stable
internal sealed interface BookEntry {
    data class Book(val bookResponse: GetBook) : BookEntry
    data object Empty : BookEntry
}

internal fun List<GetBook>.toCurrentlyReadingBookEntries(): ImmutableList<BookEntry> =
    (listOf(BookEntry.Empty) + this.map(GetBook::toEntry)).toImmutableList()

internal fun List<GetBook>.toPlanningToReadBookEntries(): ImmutableList<BookEntry> {
    val result = this.map(GetBook::toEntry).toMutableList()

    return if (result.size >= 5) {
        result.take(5)
    } else {
        result.addAll(
            List(5 - result.size) { BookEntry.Empty }
        )
        result
    }.toImmutableList()
}

internal fun GetBook.toEntry(): BookEntry = BookEntry.Book(bookResponse = this)