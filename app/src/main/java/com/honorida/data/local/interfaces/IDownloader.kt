package com.honorida.data.local.interfaces

import android.content.Context

interface IDownloader {
    fun downloadFile(context: Context, url: String) : Long
}