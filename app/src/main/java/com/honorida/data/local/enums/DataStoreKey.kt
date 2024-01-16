package com.honorida.data.local.enums
import androidx.datastore.preferences.core.stringPreferencesKey

sealed class DataStoreKey {
    companion object {
        val StorageUri = stringPreferencesKey(name = "storage_uri")
    }
}