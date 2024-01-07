package com.honorida.data.local.services

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.honorida.data.local.interfaces.Downloader

class DownloaderImpl(
    context: Context
): Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(context: Context, url: String): Long {
        val uri = url.toUri()
        val fileName = url.subSequence(url.lastIndexOf('/') + 1, url.length)

        val request = DownloadManager.Request(uri)
            .setMimeType("application/vnd.android.package-archive")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName.toString())

        return downloadManager.enqueue(request)
    }
}