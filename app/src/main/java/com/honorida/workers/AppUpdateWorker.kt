package com.honorida.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.honorida.BuildConfig
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.domain.constants.HonoridaNotification
import kotlinx.coroutines.flow.first

class AppUpdateWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val appModule = HonoridaApp.appModule

    override suspend fun doWork(): Result {
        val checkUpdatesOnStartUpEnabled =
            appModule.protoDataStore.applicationPreferences.data.first().checkUpdatesOnStartUp
        if (checkUpdatesOnStartUpEnabled) {
            appModule.appUpdater.checkForUpdates(
                context,
                BuildConfig.VERSION_NAME
            ) {
                if (it.updateRequired) {
                    appModule.notificationService.showNotification(
                        notificationId = HonoridaNotification.AppUpdate.id,
                        channelId = HonoridaNotification.AppUpdate.channelId,
                        title = context.getString(R.string.application_updates),
                        contentText = context.getString(R.string.new_app_version_is_available)
                    )
                }
            }
        }
        return Result.success()
    }
}