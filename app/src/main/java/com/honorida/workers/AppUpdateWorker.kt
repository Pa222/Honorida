package com.honorida.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.honorida.BuildConfig
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.domain.constants.HonoridaNotification

class AppUpdateWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val appModule = HonoridaApp.appModule

    override suspend fun doWork(): Result {
        val checkUpdatesOnStartUpEnabled = appModule.dataStoreRepository.getFirstPreference(
            DataStoreKey.CHECK_FOR_UPDATES_ON_STARTUP, false
        )
        if (checkUpdatesOnStartUpEnabled) {
            appModule.appUpdater.checkForUpdates(
                context,
                BuildConfig.VERSION_NAME
            ) {
                appModule.notificationService.showNotification(
                    notificationId = HonoridaNotification.AppUpdate.id,
                    channelId = HonoridaNotification.AppUpdate.channelId,
                    title = context.getString(R.string.application_update),
                    contentText = context.getString(R.string.new_app_version_is_available)
                )
            }
        }
        return Result.success()

    }
}