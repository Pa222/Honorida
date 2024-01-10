package com.honorida.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.honorida.BuildConfig
import com.honorida.HonoridaApp
import com.honorida.domain.extensions.isPreReleaseVersion
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
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_NAME.isPreReleaseVersion()
            ) {
                if (it.updateRequired) {
                    appModule.notificationService.showAppUpdateNotification(it)
                }
            }
        }
        return Result.success()
    }
}