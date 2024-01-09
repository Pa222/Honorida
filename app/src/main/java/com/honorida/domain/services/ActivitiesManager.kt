package com.honorida.domain.services

import android.content.Context
import android.content.Intent
import com.honorida.activities.appUpdate.AppUpdateActivity
import com.honorida.activities.main.MainActivity
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.services.interfaces.IActivitiesManager

class ActivitiesManager : IActivitiesManager {
    override fun startAppUpdateActivity(
        context: Context,
        updateInfo: CheckUpdateResponse
    ) {
        val activityIntent = Intent(context, AppUpdateActivity::class.java)
        activityIntent.putExtras(updateInfo.toExtras())

        context.startActivity(activityIntent)
    }

    override fun startMainActivity(context: Context) {
        val activityIntent = Intent(context, MainActivity::class.java)
        context.startActivity(activityIntent)
    }
}

sealed class Extras(
    val key: String
) {
    data object UpdateRequired: Extras("updateRequired")
    data object UpdateUrl: Extras("updateUrl")
    data object LatestAppVersion: Extras("latestAppVersion")
    data object ReleaseUrl: Extras("releaseUrl")
}