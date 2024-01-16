package com.honorida.domain.services.foreground

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AppUpdateForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}