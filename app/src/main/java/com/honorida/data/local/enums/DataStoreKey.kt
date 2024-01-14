package com.honorida.data.local.enums
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed class DataStoreKey {
    companion object {
        val StorageConfigured = booleanPreferencesKey(name = "storage_configured")
        val StorageUri = stringPreferencesKey(name = "storage_uri")
    }
}