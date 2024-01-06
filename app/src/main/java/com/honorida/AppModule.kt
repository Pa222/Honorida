package com.honorida

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.honorida.data.local.repositories.UserPreferencesRepository

interface IAppModule {
    val userPreferencesRepository: UserPreferencesRepository
}

class AppModuleImpl (
    private val appContext: Context
) : IAppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "UserPreferences"
    )

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(appContext.dataStore)
    }
}
