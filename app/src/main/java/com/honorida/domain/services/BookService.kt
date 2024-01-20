package com.honorida.domain.services

import android.app.Application
import android.widget.Toast
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.honorida.R
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.exceptions.FileNotAccessibleException
import com.honorida.domain.services.interfaces.IBookService
import javax.inject.Inject

class BookService @Inject constructor(
    private val databaseContext: HonoridaDatabase,
    private val context: Application,
): IBookService {
    override suspend fun removeBook(bookId: Int) {
        try {
            val book = databaseContext.booksDao.findById(bookId)
            if (book == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.book_not_found),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            try {
                val targetFile = DocumentFile.fromSingleUri(context, book.fileUrl.toUri())!!
                if (!targetFile.delete()) {
                    throw FileNotAccessibleException()
                }
            }
            catch (e: FileNotAccessibleException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_remove_file_from_storage_verbose),
                    Toast.LENGTH_LONG
                ).show()
            }
            catch(e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_remove_file_from_storage),
                    Toast.LENGTH_LONG
                ).show()
            }
            finally {
                databaseContext.booksDao.deleteBook(book)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                context.getString(R.string.failed_to_remove_book),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}