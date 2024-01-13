package com.honorida.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.honorida.data.models.db.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM Books")
    fun getAll(): Flow<List<Book>>
}