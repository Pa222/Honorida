package com.honorida.data.local.context

import androidx.room.Database
import androidx.room.RoomDatabase
import com.honorida.data.local.dao.BooksDao
import com.honorida.data.models.db.Book

@Database(
    entities = [
        Book::class
    ],
    version = 1
)
abstract class HonoridaDatabase: RoomDatabase() {

    abstract val booksDao: BooksDao
}