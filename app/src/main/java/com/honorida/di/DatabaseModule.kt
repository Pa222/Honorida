package com.honorida.di

import android.app.Application
import androidx.room.Room
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.data.local.dao.BooksDao
import com.honorida.data.local.repositories.DaoRepository
import com.honorida.data.local.repositories.interfaces.IDaoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindDaoRepository(
        repository: DaoRepository
    ) : IDaoRepository

    companion object {
        @Provides
        @Singleton
        fun provideHonoridaDatabase(context: Application): HonoridaDatabase {
            return Room
                .databaseBuilder(context, HonoridaDatabase::class.java, "Honorida.db")
                .allowMainThreadQueries()
                .build()
        }

        @Provides
        @Singleton
        fun provideBooksDao(database: HonoridaDatabase): BooksDao {
            return database.booksDao
        }
    }
}
