package com.honorida.domain.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.honorida.R
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.data.external.services.IHonoridaApiService
import com.honorida.domain.services.interfaces.IAppUpdater

class AppUpdater(
    private val apiService: IHonoridaApiService
) : IAppUpdater {
    override suspend fun checkForUpdates (
        context: Context,
        appVersion: String,
        checkForPreRelease: Boolean,
        callBack: (CheckUpdateResponse) -> Unit
    ) {
        try {
            val response = apiService.checkUpdates(
                appVersion,
                checkForPreRelease
            )
            callBack(response)
        } catch (e: Exception) {
            Log.e("checkForUpdates", e.message ?: "Unknown error")
            Toast.makeText(context,
                context.getText(R.string.failed_to_check_for_updates),
                Toast.LENGTH_SHORT).show()
        }
    }
}
