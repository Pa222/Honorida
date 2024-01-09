package com.honorida.activities.appUpdate.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.honorida.data.local.interfaces.IDownloader
import com.honorida.domain.services.interfaces.IActivitiesManager

class AppUpdateViewModel(
    private val downloader: IDownloader,
    private val activitiesManager: IActivitiesManager
) : ViewModel() {

    fun downloadUpdate(context: Context, url: String) {
        downloader.downloadFile(context, url)
        returnToMainActivity(context)
    }

    fun returnToMainActivity(context: Context) {
        activitiesManager.startMainActivity(context)
    }
}