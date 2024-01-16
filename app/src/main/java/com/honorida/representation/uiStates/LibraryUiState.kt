package com.honorida.representation.uiStates

import com.honorida.data.models.db.Book

data class LibraryUiState(
    val books: List<Book> = emptyList()
)