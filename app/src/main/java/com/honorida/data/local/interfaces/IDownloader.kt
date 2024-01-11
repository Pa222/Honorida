package com.honorida.data.local.interfaces

import android.content.Context

interface IDownloader {
    fun downloadFile(url: String) : Long
}