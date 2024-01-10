package com.honorida.activities.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.activities.main.ui.components.App
import com.honorida.activities.main.ui.components.navigation.DeepLinks
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.APP_UPDATES_NOTIFICATION_CHANNEL_ID
import com.honorida.domain.constants.Extras
import com.honorida.domain.extensions.asBoolean
import com.honorida.workers.AppUpdateWorker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleAppUpdatesCheck()
            createNotificationChannels()
        }

        setContent {
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.getString(Extras.OpenAppUpdatePage.key).asBoolean()) {
                    val updateInfo = CheckUpdateResponse.fromExtras(bundle)
                    startActivity(DeepLinks.AppUpdate.getIntent(LocalContext.current, updateInfo))
                }
            }
            App()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleAppUpdatesCheck() {
        val intent = intent
        if (intent.action == Intent.ACTION_MAIN){
            val appUpdateWorker = OneTimeWorkRequestBuilder<AppUpdateWorker>()
                .build()
            HonoridaApp.appModule.workManager.enqueue(appUpdateWorker)
        }
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
