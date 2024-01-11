package com.honorida.domain.services.broadcastReceivers

import android.app.DownloadManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import com.honorida.domain.extensions.getFileNameFromUrl

class AppUpdateDownloadReceiver  : BroadcastReceiver() {

    companion object {
        private const val PROVIDER_PATH = ".provider"
    }

    private lateinit var downloadManager: DownloadManager

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        // TODO: For future releases
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            return
//        }
//        try {
//            if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
//                downloadManager = context.getSystemService(DownloadManager::class.java)!!
//
//                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
//                if (id != -1L) {
//                    val query = DownloadManager.Query()
//                        .setFilterById(id)
//                    val cursor = downloadManager.query(query)
//                    if (cursor.moveToFirst()) {
//                        val index = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
//                        val downloadedFileLocalUri = cursor.getString(index)
//                        val fileExtension = downloadedFileLocalUri.getFileExtension()
//                        if (fileExtension != "apk") {
//                            return
//                        }
//                        val contentUri = FileProvider.getUriForFile(
//                            context,
//                            BuildConfig.APPLICATION_ID + PROVIDER_PATH,
//                            File(downloadedFileLocalUri)
//                        )
//                        install(
//                            context = context,
//                            apkUri = contentUri
//                        )
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(
//                context,
//                context.getString(R.string.failed_to_open_file),
//                Toast.LENGTH_LONG
//            ).show()
//        }
    }

    private fun install(context: Context, apkUri: Uri) {
        val sessionParams = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        val packageInstaller = context.packageManager.packageInstaller
        val sessionId = packageInstaller.createSession(sessionParams)
        val session = packageInstaller.openSession(sessionId)

        context.contentResolver.openInputStream(apkUri).use { apkStream ->
            requireNotNull(apkStream) { "$apkUri: InputStream was null" }
            val sessionStream = session.openWrite("${apkUri.path?.getFileNameFromUrl()}.apk", 0, -1)
            sessionStream.buffered().use { bufferedSessionStream ->
                apkStream.copyTo(bufferedSessionStream)
                bufferedSessionStream.flush()
                session.fsync(sessionStream)
            }
        }

        val receiverIntent = Intent(context, PackageInstallerStatusReceiver::class.java)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val receiverPendingIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, flags)
        session.commit(receiverPendingIntent.intentSender)
        session.close()
    }
}