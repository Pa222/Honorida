package com.honorida.data.local.eventHandlers

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.honorida.BuildConfig
import com.honorida.R
import java.io.File


// To use - uncomment receiver in AndroidManifest.xml
class DownloadCompletedReceiver : BroadcastReceiver() {

    private lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            downloadManager = context?.getSystemService(DownloadManager::class.java)!!
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (id != -1L) {
                    openDownloadedAttachment(context, id)
                }
            }
        } catch (e: Exception) {
            Log.e("DownloadCompletedReceiver", e.message ?: "Unexpected error")
            Toast.makeText(context,
                context?.getString(R.string.failed_to_open_file), Toast.LENGTH_LONG).show()
        }
    }

    private fun openDownloadedAttachment(context: Context, downloadId: Long) {
        val query = DownloadManager.Query()
            .setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            var index = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            if (index == -1) {
                return
            }
            val downloadStatus = cursor.getInt(index)
            index = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
            if (index == -1) {
                return
            }
            val downloadLocalUri = cursor.getString(index)
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                val uri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    File(downloadLocalUri))
                val install = Intent(Intent.ACTION_VIEW)
                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                install.setDataAndType(
                    uri,
                    downloadManager.getMimeTypeForDownloadedFile(downloadId)
                )
                startActivity(context, install, Bundle())
            }
        }
        cursor.close()
    }
}