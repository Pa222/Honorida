package com.honorida.data.local.repositories.interfaces

import com.honorida.data.local.dao.BooksDao

interface IDaoRepository {
    val books: BooksDao
}