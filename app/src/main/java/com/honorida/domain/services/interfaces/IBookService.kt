package com.honorida.domain.services.interfaces

interface IBookService {
    suspend fun removeBook(bookId: Int)
}