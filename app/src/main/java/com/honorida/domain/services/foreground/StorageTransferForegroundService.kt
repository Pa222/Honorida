package com.honorida.domain.services.foreground

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.net.Uri
import android.os.IBinder
import android.provider.DocumentsContract
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.honorida.R
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.helpers.isUriPersisted
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.models.ProgressInfo
import com.honorida.domain.services.interfaces.INotificationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream
import javax.inject.Inject

@AndroidEntryPoint
class StorageTransferForegroundService: Service() {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var notificationsService: INotificationService

    @Inject
    lateinit var databaseContext: HonoridaDatabase

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val sourceStorageUri = intent.extras?.getString(Extras.SourceStorageUri.key)
        val targetStorageUri = intent.extras?.getString(Extras.TargetStorageUri.key)
        if (sourceStorageUri == null || targetStorageUri == null) {
            notificationsService.showNotification(
                HonoridaNotification.FailedToTransferStorage,
                this.getString(R.string.storage_transfer),
                this.getString(R.string.failed_to_transfer_storage)
            )
            stopSelf()
        }
        else {
            start(sourceStorageUri, targetStorageUri)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(sourceStorageUri: String, targetStorageUri: String) {
        val context = this
        putNotification()
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val targetDirectory = DocumentFile.fromTreeUri(
                        context,
                        Uri.parse(targetStorageUri)
                    )!!
                    val booksFlow = databaseContext.booksDao.getAll()
                    booksFlow.collect { books ->
                        var processedBooks = 0
                        val booksCount = books.size
                        putNotification(ProgressInfo(booksCount, processedBooks))
                        books.forEach {
                            val sourceFile = DocumentFile.fromTreeUri(
                                context,
                                it.fileUrl.toUri()
                            )!!
                            val destinationFile = sourceFile.type?.let {
                                sourceFile.name?.let { it1 ->
                                    targetDirectory.createFile(it,
                                        it1
                                    )
                                }
                            }!!
                            copyFile(context, sourceFile, destinationFile)
                            databaseContext.booksDao.updateFileUrl(
                                it.id,
                                destinationFile.uri.toString()
                            )
                            processedBooks++
                            putNotification(ProgressInfo(booksCount, processedBooks))
                        }
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    notificationsService.showNotification(
                        HonoridaNotification.FailedToTransferStorage,
                        context.getString(R.string.storage_transfer),
                        context.getString(R.string.failed_to_transfer_storage)
                    )
                }
                finally {
                    if (context.contentResolver.isUriPersisted(sourceStorageUri)) {
                        val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        context.contentResolver.releasePersistableUriPermission(
                            sourceStorageUri.toUri(),
                            flags
                        )
                    }
                    notificationsService.showNotification(
                        HonoridaNotification.FailedToTransferStorage,
                        context.getString(R.string.storage_transfer),
                        context.getString(R.string.storage_transfer_finished)
                    )
                    stopSelf()
                }
            }
        }
    }

    private fun copyFile(
        context: Context,
        sourceFile: DocumentFile,
        destinationFile: DocumentFile
    ) {
        context.contentResolver.openInputStream(sourceFile.uri)?.use { input ->
            context.contentResolver.openOutputStream(destinationFile.uri)?.use { output ->
                input.copyTo(output)
            }
        }
        DocumentsContract.deleteDocument(context.contentResolver, sourceFile.uri)
    }

    private fun putNotification(
        progressInfo: ProgressInfo? = null
    ) {
        val builder = NotificationCompat.Builder(
                this,
                HonoridaNotification.AppUpdate.channelId
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.storage_transfer))
            .setContentText(getString(R.string.storage_transfer_in_progress))
            .setSilent(true)

        if (progressInfo != null) {
            builder.setProgress(
                progressInfo.max,
                progressInfo.current,
                progressInfo.indeterminate
            )
        }

        val notification = builder
            .build()

        startForeground(
            HonoridaNotification.GeneralNotification.id,
            notification,
            FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }
}