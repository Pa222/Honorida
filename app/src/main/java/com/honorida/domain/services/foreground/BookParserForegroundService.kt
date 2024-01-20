package com.honorida.domain.services.foreground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.honorida.R
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.exceptions.EntityAlreadyExistsException
import com.honorida.domain.helpers.getFileExtension
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.services.bookProcessors.helpers.BookProcessorProvider
import com.honorida.domain.services.interfaces.INotificationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    lateinit var bookProcessorProvider: BookProcessorProvider

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
        putNotification(context.getString(R.string.book_processing))
        scope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val bookProcessor = bookProcessorProvider.getBookProcessorForMimeType(
                        MimeTypes.Epub
                    )!!
                    val fileUri = uri.toUri()
                    val processedBook = bookProcessor.processBook(fileUri)
                    databaseContext.booksDao.saveBookWithTags(
                        processedBook.book,
                        processedBook.subjects
                    )
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