package com.honorida

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.honorida.data.external.services.IHonoridaApiService
import com.honorida.data.local.interfaces.IDownloader
import com.honorida.data.local.repositories.DataStoreRepository
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.data.local.services.Downloader
import com.honorida.data.local.repositories.ProtoDataStore
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.domain.services.AppUpdater
import com.honorida.domain.services.NotificationService
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.domain.services.interfaces.INotificationService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

interface IAppModule {
    val dataStoreRepository: IDataStoreRepository
    val protoDataStore: IProtoDataStore
    val honoridaApiService: IHonoridaApiService
    val downloader: IDownloader
    val appUpdater: IAppUpdater
    val notificationService: INotificationService
    val workManager: WorkManager
}

class AppModuleImpl (
    private val appContext: Context
) : IAppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "UserPreferences"
    )

    override val dataStoreRepository: IDataStoreRepository by lazy {
        DataStoreRepository(appContext.dataStore)
    }

    override val protoDataStore: IProtoDataStore by lazy {
        ProtoDataStore(appContext)
    }

    override val honoridaApiService: IHonoridaApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(IHonoridaApiService.ROOT_URL)
            .build()
            .create()
    }

    override val downloader: IDownloader by lazy {
        Downloader(appContext)
    }

    override val appUpdater: IAppUpdater by lazy {
        AppUpdater(honoridaApiService)
    }

    override val notificationService: INotificationService by lazy {
        NotificationService(appContext)
    }

    override val workManager: WorkManager by lazy {
        WorkManager.getInstance(appContext)
    }
}
