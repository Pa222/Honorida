package com.honorida.data.local.interfaces

interface IDownloader {
    fun downloadFile(url: String) : Long
}