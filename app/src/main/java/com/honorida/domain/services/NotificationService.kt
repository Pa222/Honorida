package com.honorida.domain.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.honorida.R

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(
        notificationId: Int,
        channelId: String,
        title: String,
        contentText: String,
        swappable: Boolean = true,
        iconResourceId: Int = R.drawable.logo64_64,
        activityIntent: PendingIntent? = null
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(iconResourceId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setOngoing(!swappable)

        if (activityIntent != null) {
            notificationBuilder.setContentIntent(activityIntent)
        }

        val notification = notificationBuilder.build()
        notificationManager.notify(notificationId, notification)
    }
}