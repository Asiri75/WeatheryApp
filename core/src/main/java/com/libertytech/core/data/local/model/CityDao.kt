package com.libertytech.core.data.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getAllCities(): Flow<List<CityEntity>>

    @Insert
    suspend fun insertCity(city: CityEntity)
}