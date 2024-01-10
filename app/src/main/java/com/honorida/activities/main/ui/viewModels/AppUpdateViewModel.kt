package com.honorida.activities.main.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.honorida.data.local.interfaces.IDownloader

class AppUpdateViewModel(
    private val downloader: IDownloader
) : ViewModel() {

    fun downloadUpdate(context: Context, url: String) {
        downloader.downloadFile(context, url)
    }
}