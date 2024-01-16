package com.honorida.data.local.context

import androidx.room.Database
import androidx.room.RoomDatabase
import com.honorida.data.local.dao.BooksDao
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag

@Database(
    entities = [
        Book::class,
        Tag::class
    ],
    version = 2
)
abstract class HonoridaDatabase: RoomDatabase() {

    abstract val booksDao: BooksDao
}