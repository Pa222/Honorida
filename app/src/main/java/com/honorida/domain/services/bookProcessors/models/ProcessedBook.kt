package com.honorida.domain.services.bookProcessors.models

import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag

data class ProcessedBook(
    val book: Book,
    val subjects: List<Tag> = emptyList()
)