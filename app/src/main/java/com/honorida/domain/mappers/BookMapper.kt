package com.honorida.domain.mappers

import com.honorida.data.models.db.Book
import com.honorida.representation.uiStates.BookInfo

sealed class BookMapper {
    companion object {
        fun mapToInfoModel(book: Book): BookInfo {
            return BookInfo(
                id = book.id,
                fileUrl = book.fileUrl,
                coverImage = book.coverImage,
                language = book.language,
                description = book.description,
                publishers = book.publishers,
                title = book.title,
                authors = book.authors
            )
        }
    }
}