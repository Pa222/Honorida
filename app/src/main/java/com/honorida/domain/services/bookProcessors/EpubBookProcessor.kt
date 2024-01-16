package com.honorida.domain.services.bookProcessors

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.services.bookProcessors.interfaces.IBookProcessor
import com.honorida.domain.services.bookProcessors.models.ProcessedBook
import io.documentnode.epub4j.epub.EpubReader
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class EpubBookProcessor @Inject constructor(
    private val context: Application,
    private val preferencesRepository: IDataStoreRepository
): IBookProcessor {
    override suspend fun processBook(fileUri: Uri): ProcessedBook {
        val reader = EpubReader()
        val inputStream = context.contentResolver.openInputStream(fileUri)
        val book = reader.readEpub(inputStream)
        val bookTitle = book.title

        val fileName = "${bookTitle}.epub"
        val directoryUri = Uri.parse(
            preferencesRepository.getPreference(
                DataStoreKey.StorageUri,
                ""
            ).first()
        )

        val targetDirectory = DocumentFile.fromTreeUri(context, directoryUri)!!

        val targetFile = targetDirectory.createFile(MimeTypes.Epub, fileName)
        val outputStream = context.contentResolver.openOutputStream(targetFile!!.uri)

        context.contentResolver.openInputStream(fileUri)?.use { input ->
            outputStream?.use { output ->
                input.copyTo(output)
            }
        }
        val bookCover = book.coverImage.data
        val bookSubjects = book.metadata.subjects
        val bookLanguage = book.metadata.language
        val bookPublishers = book.metadata.publishers
        val bookAuthors = book.metadata.authors
        val description = book.metadata.descriptions.firstOrNull()
        val dbBook = Book(
            title = bookTitle,
            coverImage = bookCover,
            language = bookLanguage,
            publishers = bookPublishers.joinToString(", "),
            authors = bookAuthors.joinToString(", ") {
                "${it.firstname} ${it.lastname}"
            },
            description = description,
            fileUrl = targetFile.uri.toString()
        )
        val dbTags = bookSubjects.map { subject ->
            var name = subject.replace('-', ' ')
            if (name.contains('_')) {
                name = name
                    .substring(name.indexOf('_') + 1)
                    .trim()
            }
            Tag(name = name)
        }
        return ProcessedBook(
            dbBook,
            dbTags
        )
    }
}