package com.honorida.domain.services.interfaces

import android.content.Context
import com.honorida.data.external.models.CheckUpdateResponse

interface IActivitiesManager {
    fun startAppUpdateActivity(
        context: Context,
        updateInfo: CheckUpdateResponse
    )

    fun startMainActivity(context: Context)
}