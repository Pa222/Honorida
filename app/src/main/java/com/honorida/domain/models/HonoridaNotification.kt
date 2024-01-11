package com.honorida.domain.models

import com.honorida.domain.constants.APP_UPDATES_NOTIFICATION_CHANNEL_ID

sealed class HonoridaNotification(
    val id: Int,
    val channelId: String
) {
    data object AppUpdate: HonoridaNotification(1, APP_UPDATES_NOTIFICATION_CHANNEL_ID)
}
