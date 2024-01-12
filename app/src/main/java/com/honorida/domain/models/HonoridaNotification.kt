package com.honorida.domain.models

import com.honorida.domain.constants.notifications.APP_UPDATES_NOTIFICATION_CHANNEL_ID
import com.honorida.domain.constants.notifications.GENERAL_NOTIFICATION_CHANNEL_ID

sealed class HonoridaNotification(
    val id: Int,
    val channelId: String
) {
    data object AppUpdate: HonoridaNotification(1, APP_UPDATES_NOTIFICATION_CHANNEL_ID)
    data object GeneralNotification: HonoridaNotification(2, GENERAL_NOTIFICATION_CHANNEL_ID)
}
