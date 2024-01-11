package com.honorida.domain.constants

import android.os.Build
import androidx.annotation.RequiresApi

sealed class Permissions(
    val permission: String,
    val name: String
) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object PostNotifications: Permissions(
        permission = android.Manifest.permission.POST_NOTIFICATIONS,
        name = "Send notifications"
    )

    companion object {
        fun getByString(permission: String): Permissions? {
            return when (permission) {
                android.Manifest.permission.POST_NOTIFICATIONS -> PostNotifications
                else -> null
            }
        }
    }
}