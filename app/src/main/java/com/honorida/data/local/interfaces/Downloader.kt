package com.honorida.data.local.interfaces

import android.content.Context

interface Downloader {
    fun downloadFile(context: Context, url: String) : Long
}