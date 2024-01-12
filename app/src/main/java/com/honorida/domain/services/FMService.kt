package com.honorida.domain.services

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.honorida.HonoridaApp
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.notifications.APP_UPDATE
import com.honorida.domain.models.HonoridaNotification

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        try {
            when(message.data[Extras.NotificationType.key]) {
                APP_UPDATE -> {
                    val updateInfo = CheckUpdateResponse(
                        updateRequired = true,
                        releaseId = message.data[Extras.ReleaseId.key]!!.toInt()
                    )
                    HonoridaApp.appModule.notificationService.showAppUpdateNotification(updateInfo)
                }
                null -> {
                    //
                }
                else -> {
                    HonoridaApp.appModule.notificationService.showNotification(
                        HonoridaNotification.GeneralNotification,
                        message.notification?.title!!,
                        message.notification?.body!!
                    )
                }
            }

        }
        catch(e: Exception) {
            e.printStackTrace()
        }
    }
}