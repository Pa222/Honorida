package com.honorida.di

import android.app.Application
import androidx.room.Room
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.data.local.migrations.Migration1to2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideHonoridaDatabase(context: Application): HonoridaDatabase {
            return Room
                .databaseBuilder(context, HonoridaDatabase::class.java, "Honorida.db")
                .allowMainThreadQueries()
                .addMigrations(Migration1to2)
                .build()
        }
    }
}
