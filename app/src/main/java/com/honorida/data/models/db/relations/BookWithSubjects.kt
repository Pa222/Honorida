package com.honorida.data.models.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag

data class BookWithSubjects(
    @Embedded
    val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val subjects: List<Tag>
)