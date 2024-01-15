package com.honorida.domain.helpers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import com.honorida.R
import java.util.Objects


fun getDisplayPath(context: Context, uri: Uri): String {
    if (uri.path == "") {
        return ""
    }
    val lastPathSegment = uri.lastPathSegment!!
    val backupVolume = lastPathSegment.replaceFirst(":.*".toRegex(), "")
    val backupName = "/storage/emulated/0/" + lastPathSegment.replaceFirst(".*:".toRegex(), "")
    val storageManager = context.getSystemService(StorageManager::class.java)
    val storageVolumes = storageManager.storageVolumes
    var storageVolume: StorageVolume? = null
    for (volume in storageVolumes) {
        if (Objects.equals(volume.uuid, backupVolume)) {
            storageVolume = volume
            break
        }
    }
    return if (storageVolume == null) {
        backupName
    } else {
        context.getString(
            R.string.StorageUtil__s_s,
            storageVolume.getDescription(context),
            backupName
        )
    }
}

fun checkUriPersisted(contentResolver: ContentResolver, uri: Uri): Boolean {
    return contentResolver.persistedUriPermissions.any { perm -> perm.uri == uri }
}