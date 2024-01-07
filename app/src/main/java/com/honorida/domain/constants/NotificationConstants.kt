package com.honorida.domain.constants

sealed class HonoridaNotification(
    val id: Int,
    val channelId: String
) {
    data object AppUpdate: HonoridaNotification(1, APP_UPDATES_NOTIFICATION_CHANNEL_ID)
}

const val APP_UPDATES_NOTIFICATION_CHANNEL_ID: String = "HONORIDA_NOTIFICATIONS"
