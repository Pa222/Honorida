package com.honorida.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag
import com.honorida.domain.exceptions.EntityAlreadyExistsException
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BooksDao {

    @Query("SELECT * FROM Books")
    abstract fun getAll(): Flow<List<Book>>

    @Query("SELECT * FROM Books where title = :name")
    abstract suspend fun findByTitle(name: String): Book?

    @Query("SELECT * FROM Books where id = :id")
    abstract suspend fun findById(id: Int): Book?

    @Upsert
    abstract suspend fun saveBook(book: Book): Long

    @Upsert
    abstract suspend fun saveTags(tags: List<Tag>)

    @Delete
    abstract suspend fun deleteBook(book: Book)

    @Query("UPDATE Books set fileUrl = :fileUrl where id = :bookId")
    abstract suspend fun updateFileUrl(bookId: Int, fileUrl: String)

    @Transaction
    open suspend fun saveBookWithTags(
        book: Book,
        tags: List<Tag>
    ) {
        val existingBook = findByTitle(book.title)
        if (existingBook != null) {
            throw EntityAlreadyExistsException()
        }
        val bookId = saveBook(book).toInt()
        val tagsToCreate = tags.map {
            it.copy(bookId = bookId)
        }
        saveTags(tagsToCreate)
    }
}