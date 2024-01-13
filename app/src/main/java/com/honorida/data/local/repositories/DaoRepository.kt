package com.honorida.data.local.repositories

import com.honorida.data.local.dao.BooksDao
import com.honorida.data.local.repositories.interfaces.IDaoRepository
import javax.inject.Inject

class DaoRepository @Inject constructor(
    override val books: BooksDao
): IDaoRepository {

}