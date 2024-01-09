package com.honorida.domain.services.interfaces

import android.content.Context
import com.honorida.data.external.models.CheckUpdateResponse

interface IAppUpdater {
    suspend fun checkForUpdates (
        context: Context,
        appVersion: String,
        callBack: (CheckUpdateResponse) -> Unit
    )
}