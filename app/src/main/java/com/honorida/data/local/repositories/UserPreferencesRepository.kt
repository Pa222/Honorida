package com.honorida.data.local.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.local.enums.UserPreferencesKey
import com.honorida.data.local.enums.toDarkThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    dataStore: DataStore<Preferences>
) {
    val darkThemePreference: Flow<DarkThemePreference> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[UserPreferencesKey.USE_DARK_THEME]?.toDarkThemePreference()
                ?: DarkThemePreference.FOLLOW_SYSTEM
        }
}