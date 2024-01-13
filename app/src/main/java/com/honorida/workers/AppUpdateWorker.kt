package com.honorida.workers

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.honorida.BuildConfig
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.domain.extensions.isPreReleaseVersion
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.domain.services.interfaces.INotificationService
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltWorker
class AppUpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val appUpdater: Lazy<IAppUpdater>,
    private val protoDataStore: IProtoDataStore,
    private val notificationService: Lazy<INotificationService>,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val updatesPreferences =
            protoDataStore.updatesPreferences.data.first()
        if (updatesPreferences.receiveAppUpdates && updatesPreferences.checkUpdatesOnStartUp) {
            appUpdater.get().checkForUpdates(
                context,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_NAME.isPreReleaseVersion()
            ) {
                if (it.updateRequired) {
                    notificationService.get().showAppUpdateNotification(it)
                }
            }
        }
        return Result.success()
    }
}