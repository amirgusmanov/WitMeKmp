package kz.witme.project.dashboard

import androidx.compose.runtime.Stable

@Stable
sealed interface BookEntry {
    data class Book(val bookResponse: kz.witme.project.dashboard.Book) : BookEntry
    data object Empty : BookEntry
}

internal fun List<Book>.toEntries(): List<BookEntry> {
    val result = this.map(Book::toEntry).toMutableList()

    return if (result.size >= 5) {
        result.take(5)
    } else {
        result.addAll(
            List(5 - result.size) { BookEntry.Empty }
        )
        result
    }
}

internal fun Book.toEntry(): BookEntry = BookEntry.Book(bookResponse = this)