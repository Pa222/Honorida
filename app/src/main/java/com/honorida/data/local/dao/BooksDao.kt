package com.honorida.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BooksDao {

    @Query("SELECT * FROM Books")
    abstract fun getAll(): Flow<List<Book>>

    @Upsert
    abstract suspend fun saveBook(book: Book): Long

    @Upsert
    abstract suspend fun saveTags(tags: List<Tag>)

    @Transaction
    open suspend fun saveBookWithTags(
        book: Book,
        tags: List<Tag>
    ) {
        val bookId = saveBook(book).toInt()
        val tagsToCreate = tags.map {
            it.copy(bookId = bookId)
        }
        saveTags(tagsToCreate)
    }
}