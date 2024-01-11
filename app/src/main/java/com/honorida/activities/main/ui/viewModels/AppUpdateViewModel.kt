package com.honorida.activities.main.ui.viewModels

import androidx.lifecycle.ViewModel
import com.honorida.data.local.interfaces.IDownloader

class AppUpdateViewModel(
    private val downloader: IDownloader
) : ViewModel() {

    fun downloadUpdate(url: String) {
        downloader.downloadFile(url)
    }
}