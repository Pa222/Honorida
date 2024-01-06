package com.honorida

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.honorida.data.local.repositories.UserPreferencesRepository

class HonoridaApplication : Application() {
    companion object {
        lateinit var appModule: IAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}