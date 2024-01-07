package com.honorida.data.local.enums

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object DataStoreKey {
    val DARK_THEME_PREFERENCE = intPreferencesKey("USE_DARK_THEME")
    val CHECK_FOR_UPDATES_ON_STARTUP = booleanPreferencesKey("CHECK_FOR_UPDATES_ON_STARTUP")
}