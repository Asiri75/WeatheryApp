package com.libertytech.core.data.repository

import com.libertytech.core.data.local.DatabaseModule
import com.libertytech.core.data.local.model.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [DatabaseModule::class])
class RepoModule {

    @Singleton
    @Provides
    fun provideCityRepository(cityDao: CityDao): CityRepository {
        return CityRepository(cityDao)
    }
}