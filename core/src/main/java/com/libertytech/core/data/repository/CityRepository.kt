package com.libertytech.core.data.repository

import com.libertytech.core.data.local.model.CityDao
import com.libertytech.core.data.local.model.CityEntity
import javax.inject.Inject

class CityRepository @Inject constructor(private val cityDao: CityDao) {

    suspend fun addCity(id: String, name: String, lat: Double, lng: Double) =
        cityDao.insertCity(
            CityEntity(
                id = id,
                name = name,
                lat = lat,
                lng = lng
            )
        )

    fun getAllCities() = cityDao.getAllCities()
}