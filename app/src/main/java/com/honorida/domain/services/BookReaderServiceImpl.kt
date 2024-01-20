package com.honorida.domain.services

import android.app.Application
import android.net.Uri
import androidx.core.text.HtmlCompat
import com.honorida.domain.services.interfaces.IBookReaderService
import com.honorida.domain.services.models.ChapterInfo
import io.documentnode.epub4j.domain.Book
import io.documentnode.epub4j.epub.EpubReader
import java.nio.charset.Charset
import javax.inject.Inject

class BookReaderServiceImpl @Inject constructor(
    private val context: Application
): IBookReaderService {

    override fun getChapters(bookFileUri: Uri): List<ChapterInfo> {
        val book = getBook(bookFileUri)
        val toc = book.tableOfContents.tocReferences
        val chapters = toc.map {
            ChapterInfo(
                title = it.title,
                resourceId = it.resourceId
            )
        }
        return chapters
    }

    override fun getResourceContent(bookFileUri: Uri, resourceId: String): String {
        val book = getBook(bookFileUri)
        val resource = book.resources.getById(resourceId)
        val resourceDataAsString = resource.data.toString(Charset.forName(resource.inputEncoding))
        return HtmlCompat.fromHtml(
            resourceDataAsString,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        ).toString()
    }

    private fun getBook(bookFileUri: Uri): Book {
        val reader = EpubReader()
        val inputStream = context.contentResolver.openInputStream(bookFileUri)
        return reader.readEpub(inputStream)
    }
}