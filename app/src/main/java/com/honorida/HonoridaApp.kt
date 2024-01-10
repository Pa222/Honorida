package com.honorida

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Data
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
    }
}