package com.honorida.domain.services.interfaces

import android.app.PendingIntent
import com.honorida.R
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.models.HonoridaNotification
import com.honorida.domain.models.ProgressInfo

interface INotificationService {
    fun showNotification(
        notification: HonoridaNotification,
        title: String,
        contentText: String,
        swappable: Boolean = true,
        iconResourceId: Int = R.drawable.logo64_64,
        activityIntent: PendingIntent? = null,
        silent: Boolean = false,
        progressInfo: ProgressInfo? = null
    )

    fun showAppUpdateNotification(updateInfo: CheckUpdateResponse)
}