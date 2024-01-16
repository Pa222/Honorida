package com.honorida.domain.helpers

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri

fun ContentResolver.isUriPersisted(uri: Uri): Boolean {
    return this.persistedUriPermissions.any { perm -> perm.uri == uri }
}

fun ContentResolver.isUriPersisted(uri: String): Boolean {
    return try {
        this.isUriPersisted(uri.toUri())
    }
    catch (e: Exception) {
        false
    }
}