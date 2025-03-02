package kz.witme.project.book.data.local.runtime

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kz.witme.project.book.data.model.response.GetBookResponse

internal class BooksStorage {

    private val _books = MutableStateFlow<List<GetBookResponse>>(emptyList())
    val books: StateFlow<List<GetBookResponse>> = _books.asStateFlow()

    fun updateBooks(books: List<GetBookResponse>) {
        _books.update {
            books
        }
    }
}