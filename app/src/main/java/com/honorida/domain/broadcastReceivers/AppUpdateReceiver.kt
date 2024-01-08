package com.honorida.domain.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.honorida.HonoridaApp
import com.honorida.data.external.models.CheckUpdateResponse

class AppUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.extras != null) {
            HonoridaApp.appModule.activitiesManager.startAppUpdateActivity(
                context = context,
                updateInfo = CheckUpdateResponse.fromExtras(intent.extras!!)
            )
        }
    }
}