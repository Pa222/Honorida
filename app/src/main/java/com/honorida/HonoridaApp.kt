package com.honorida

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import com.honorida.domain.constants.APP_UPDATES_NOTIFICATION_CHANNEL_ID
import com.honorida.workers.AppUpdateWorker

class HonoridaApp : Application() {
    companion object {
        lateinit var appModule: IAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleAppUpdatesCheck()
            createNotificationChannels()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleAppUpdatesCheck() {
        val appUpdateWorker = OneTimeWorkRequestBuilder<AppUpdateWorker>().build()
        appModule.workManager.enqueue(appUpdateWorker)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {
        createNotificationChannel(
            APP_UPDATES_NOTIFICATION_CHANNEL_ID,
            getString(R.string.app_update_notifications),
            description =
            getString(R.string.notifications_of_a_new_app_version_available),
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        id: String,
        name: String,
        description: String,
        importance: Int
    ) {
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}