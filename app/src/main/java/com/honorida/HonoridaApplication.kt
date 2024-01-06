package com.honorida

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.honorida.data.local.repositories.UserPreferencesRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "UserPreferences"
)

class HonoridaApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}