package com.honorida.domain.services.interfaces

interface IDownloader {
    fun downloadFile(url: String) : Long
}