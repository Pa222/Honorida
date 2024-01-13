package com.honorida.domain.services

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.notifications.APP_UPDATE
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.services.interfaces.INotificationService
import javax.inject.Inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FMService @Inject constructor(
    private val notificationService: INotificationService
) : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        try {
            when(message.data[Extras.NotificationType.key]) {
                APP_UPDATE -> {
                    val updateInfo = CheckUpdateResponse(
                        updateRequired = true,
                        releaseId = message.data[Extras.ReleaseId.key]!!.toInt()
                    )
                    notificationService.showAppUpdateNotification(updateInfo)
                }
                null -> {
                    //
                }
                else -> {
                    notificationService.showNotification(
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