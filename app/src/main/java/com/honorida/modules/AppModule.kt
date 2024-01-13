package com.honorida.modules

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.honorida.BuildConfig
import com.honorida.data.external.services.IHonoridaApiService
import com.honorida.domain.services.interfaces.IDownloader
import com.honorida.data.local.repositories.DataStoreRepository
import com.honorida.data.local.repositories.ProtoDataStore
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.domain.services.Downloader
import com.honorida.domain.services.AppUpdater
import com.honorida.domain.services.NotificationService
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.domain.services.interfaces.INotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "UserPreferences"
    )

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Application): IDataStoreRepository {
        return DataStoreRepository(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideProtoDataStore(context: Application): IProtoDataStore {
        return ProtoDataStore(context)
    }

    @Provides
    @Singleton
    fun provideHonoridaApiService(): IHonoridaApiService {
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()
            .create(IHonoridaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDownloader(context: Application): IDownloader {
        return Downloader(context)
    }

    @Provides
    @Singleton
    fun provideAppUpdater(
        honoridaApiService: IHonoridaApiService,
        downloader: IDownloader,
        protoDataStore: IProtoDataStore,
    ): IAppUpdater {
        return AppUpdater(
            honoridaApiService,
            downloader,
            protoDataStore,
        )
    }

    @Provides
    @Singleton
    fun providesNotificationService(context: Application): INotificationService {
        return NotificationService(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Application): WorkManager {
        return WorkManager.getInstance(context)
    }
}
