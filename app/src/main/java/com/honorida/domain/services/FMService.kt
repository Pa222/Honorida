package com.honorida.domain.services

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.honorida.HonoridaApp
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val updateInfo = CheckUpdateResponse(
            updateRequired = true,
            updateUrl = message.data[Extras.UpdateUrl.key],
            latestAppVersion = message.data[Extras.LatestAppVersion.key],
            releaseUrl = message.data[Extras.ReleaseUrl.key]
        )
        HonoridaApp.appModule.notificationService.showAppUpdateNotification(updateInfo)
    }
}