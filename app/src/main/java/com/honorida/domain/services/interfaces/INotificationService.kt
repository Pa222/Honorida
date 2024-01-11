package com.honorida.domain.services.interfaces

import android.app.PendingIntent
import com.honorida.R
import com.honorida.data.external.models.CheckUpdateResponse

interface INotificationService {
    fun showNotification(
        notificationId: Int,
        channelId: String,
        title: String,
        contentText: String,
        swappable: Boolean = true,
        iconResourceId: Int = R.drawable.logo64_64,
        activityIntent: PendingIntent? = null
    )

    fun showAppUpdateNotification(updateInfo: CheckUpdateResponse)
}