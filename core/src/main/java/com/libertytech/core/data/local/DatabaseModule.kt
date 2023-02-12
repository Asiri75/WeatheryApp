package com.libertytech.core.data.local

import android.content.Context
import androidx.room.Room
import com.libertytech.core.data.local.model.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "WeatheryDatabase"
        ).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): CityDao {
        return appDatabase.cityDao()
    }

}