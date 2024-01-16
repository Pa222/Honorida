package com.honorida.domain.services

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import androidx.core.app.NotificationCompat
import com.honorida.R
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.APP_UPDATE_NOTIFICATION_ACTIVITY_REQUEST
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.services.interfaces.INotificationService
import com.honorida.ui.components.navigation.DeepLinks

class NotificationService(
    private val context: Application
) : INotificationService {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(
        notification: HonoridaNotification,
        title: String,
        contentText: String,
        swappable: Boolean,
        iconResourceId: Int,
        activityIntent: PendingIntent?,
        silent: Boolean,
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, notification.channelId)
            .setSmallIcon(iconResourceId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setOngoing(!swappable)
            .setAutoCancel(true)
            .setSilent(silent)

        if (activityIntent != null) {
            notificationBuilder.setContentIntent(activityIntent)
        }

        val builtNotification = notificationBuilder.build()
        notificationManager.notify(notification.id, builtNotification)
    }

    override fun showAppUpdateNotification(updateInfo: CheckUpdateResponse) {
        val deepLinkIntent = DeepLinks.AppUpdate.getIntent(context, updateInfo)

        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder
            .create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(
                APP_UPDATE_NOTIFICATION_ACTIVITY_REQUEST,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        showNotification(
            notification = HonoridaNotification.AppUpdate,
            title = context.getString(R.string.application_updates),
            contentText = context.getString(R.string.new_version_available),
            activityIntent = deepLinkPendingIntent,
        )
    }
}