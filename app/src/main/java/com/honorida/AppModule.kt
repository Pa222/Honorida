package com.honorida

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.honorida.data.external.services.HonoridaApiService
import com.honorida.data.local.interfaces.Downloader
import com.honorida.data.local.repositories.DataStoreRepository
import com.honorida.data.local.services.DownloaderImpl
import com.honorida.domain.services.AppUpdater
import com.honorida.domain.services.NotificationService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

interface IAppModule {
    val dataStoreRepository: DataStoreRepository
    val honoridaApiService: HonoridaApiService
    val downloader: Downloader
    val appUpdater: AppUpdater
    val notificationService: NotificationService
    val workManager: WorkManager
}

class AppModuleImpl (
    private val appContext: Context
) : IAppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "UserPreferences"
    )

    override val dataStoreRepository: DataStoreRepository by lazy {
        DataStoreRepository(appContext.dataStore)
    }

    override val honoridaApiService: HonoridaApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(HonoridaApiService.ROOT_URL)
            .build()
            .create()
    }

    override val downloader: Downloader by lazy {
        DownloaderImpl(appContext)
    }

    override val appUpdater: AppUpdater by lazy {
        AppUpdater(honoridaApiService)
    }

    override val notificationService: NotificationService by lazy {
        NotificationService(appContext)
    }
    override val workManager: WorkManager by lazy {
        WorkManager.getInstance(appContext)
    }
}
