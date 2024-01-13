package com.honorida.domain.services

import android.app.Application
import android.app.DownloadManager
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import com.honorida.R
import com.honorida.domain.services.interfaces.IDownloader
import com.honorida.domain.extensions.getFileNameFromUrl

class Downloader(
    private val context: Application
): IDownloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    companion object {
        private const val APK_MIME_TYPE = "application/vnd.android.package-archive"
    }

    override fun downloadFile(url: String): Long {
        return try {
            val uri = url.toUri()
            val fileName = url.getFileNameFromUrl()!!
            val request = DownloadManager.Request(uri)
                .setMimeType(APK_MIME_TYPE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            downloadManager.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                context.getString(R.string.failed_to_start_download),
                Toast.LENGTH_LONG
            )
                .show()
            0L
        }
    }
}