package com.honorida.domain.services.foreground

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.honorida.R
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.data.models.db.Book
import com.honorida.data.models.db.Tag
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.exceptions.EntityAlreadyExistsException
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.services.interfaces.INotificationService
import dagger.hilt.android.AndroidEntryPoint
import io.documentnode.epub4j.epub.EpubReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class BookParserForegroundService: Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var databaseContext: HonoridaDatabase

    @Inject
    lateinit var notificationsService: INotificationService

    @Inject
    lateinit var preferencesRepository: IDataStoreRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val uri = intent.extras?.getString(Extras.FileUri.key)
        if (uri != null) {
            start(uri)
        }
        else {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(uri: String) {
        val context = this;
        putNotification("")
        scope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val fileUri = uri.toUri()
                    val reader = EpubReader()
                    val inputStream = contentResolver.openInputStream(fileUri)
                    val book = reader.readEpub(inputStream)
                    val bookTitle = book.title
                    putNotification(book.title)

                    val fileName = "${bookTitle}.epub"
                    val directoryUri = Uri.parse(
                        preferencesRepository.getPreference(
                            DataStoreKey.StorageUri,
                            ""
                        ).first()
                    )

                    val targetDirectory = DocumentFile.fromTreeUri(context, directoryUri)!!

                    val targetFile = targetDirectory.createFile(MimeTypes.Epub, fileName)
                    val outputStream = contentResolver.openOutputStream(targetFile!!.uri)

                    contentResolver.openInputStream(fileUri)?.use { input ->
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
                    databaseContext.booksDao.saveBookWithTags(dbBook, dbTags)
                }
                catch (e: EntityAlreadyExistsException) {
                    notificationsService.showNotification(
                        HonoridaNotification.BookFailedToProcess,
                        context.getString(R.string.book_processing),
                        context.getString(R.string.book_already_exists)
                    )
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    notificationsService.showNotification(
                        HonoridaNotification.BookFailedToProcess,
                        context.getString(R.string.book_processing),
                        context.getString(R.string.failed_to_process_book)
                    )
                }
                finally {
                    stopSelf()
                }
            }
        }
    }

    private fun copy(source: File, destination: File) {

        val input = FileInputStream(source).channel
        val output = FileOutputStream(destination).channel

        try {
            input.transferTo(0, input.size(), output);
        }
        finally {
            input?.close();
            output?.close();
        }
    }

    private fun putNotification(contentText: String) {
        val notification = NotificationCompat.Builder(
                this,
                HonoridaNotification.AppUpdate.channelId
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(this.getString(R.string.book_processing))
            .setContentText(contentText)
            .setSilent(true)
            .build()

        startForeground(
            HonoridaNotification.GeneralNotification.id,
            notification
        )
    }
}