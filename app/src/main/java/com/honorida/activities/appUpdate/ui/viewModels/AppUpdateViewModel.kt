package com.honorida.activities.appUpdate.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.honorida.data.local.interfaces.Downloader
import com.honorida.domain.services.ActivitiesManager

class AppUpdateViewModel(
    private val downloader: Downloader,
    private val activitiesManager: ActivitiesManager
) : ViewModel() {

    fun downloadUpdate(context: Context, url: String) {
        downloader.downloadFile(context, url)
        returnToMainActivity(context)
    }

    fun returnToMainActivity(context: Context) {
        activitiesManager.startMainActivity(context)
    }
}