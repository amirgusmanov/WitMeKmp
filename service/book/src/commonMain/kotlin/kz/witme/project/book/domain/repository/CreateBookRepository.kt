package kz.witme.project.book.domain.repository

import kz.witme.project.book.domain.model.CreateBookRequest

interface CreateBookRepository {

    suspend fun createBook(request: CreateBookRequest)
}