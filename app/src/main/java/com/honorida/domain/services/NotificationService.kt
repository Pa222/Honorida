package com.honorida.domain.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import androidx.core.app.NotificationCompat
import com.honorida.R
import com.honorida.activities.main.ui.components.navigation.DeepLinks
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.APP_UPDATE_NOTIFICATION_ACTIVITY_REQUEST
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.services.interfaces.INotificationService

class NotificationService(
    private val context: Context
) : INotificationService {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(
        notificationId: Int,
        channelId: String,
        title: String,
        contentText: String,
        swappable: Boolean,
        iconResourceId: Int,
        activityIntent: PendingIntent?
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(iconResourceId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setOngoing(!swappable)
            .setAutoCancel(true)

        if (activityIntent != null) {
            notificationBuilder.setContentIntent(activityIntent)
        }

        val notification = notificationBuilder.build()
        notificationManager.notify(notificationId, notification)
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
            notificationId = HonoridaNotification.AppUpdate.id,
            channelId = HonoridaNotification.AppUpdate.channelId,
            title = context.getString(R.string.application_updates),
            contentText = context.getString(R.string.new_version_available),
            activityIntent = deepLinkPendingIntent,
        )
    }
}