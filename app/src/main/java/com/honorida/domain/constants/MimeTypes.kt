package com.honorida.domain.constants

sealed class MimeTypes {
    companion object {
        const val Epub = "application/epub+zip"
        const val Apk = "application/vnd.android.package-archive"

        val ExtensionsMimeTypes = mapOf(
            FileExtensions.Epub to Epub,
            FileExtensions.Apk to Apk
        )
    }
}
