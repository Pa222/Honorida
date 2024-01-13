package com.honorida

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
import androidx.work.WorkManager
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.notifications.APP_UPDATES_NOTIFICATION_CHANNEL_ID
import com.honorida.domain.extensions.asBoolean
import com.honorida.ui.components.App
import com.honorida.ui.components.firebase.FireBase
import com.honorida.ui.components.navigation.DeepLinks
import com.honorida.workers.AppUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels()
        }

        setContent {
            val bundle = intent.extras
            var scheduleUpdateCheck = true
            if (bundle != null) {
                if (bundle.getString(Extras.OpenAppUpdatePage.key).asBoolean()) {
                    scheduleUpdateCheck = false
                    val updateInfo = CheckUpdateResponse.fromExtras(bundle)
                    if (updateInfo != null) {
                        startActivity(DeepLinks.AppUpdate.getIntent(LocalContext.current, updateInfo))
                    }
                }
            }
            if (scheduleUpdateCheck && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                scheduleAppUpdatesCheck()
            }
            FireBase()
            App()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleAppUpdatesCheck() {
        val intent = intent
        if (intent.action == Intent.ACTION_MAIN){
            val appUpdateWorker = OneTimeWorkRequestBuilder<AppUpdateWorker>()
                .build()
            workManager.enqueue(appUpdateWorker)
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
