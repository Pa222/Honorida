package com.honorida.domain.services.interfaces

import android.content.Context
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.data.external.models.VersionInfoResponse

interface IAppUpdater {
    suspend fun checkForUpdates (
        context: Context,
        appVersion: String,
        callBack: (CheckUpdateResponse) -> Unit
    )

    fun startUpdate(versionInfo: VersionInfoResponse)
}