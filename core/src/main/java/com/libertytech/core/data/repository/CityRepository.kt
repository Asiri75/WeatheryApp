package com.libertytech.core.data.repository

import com.libertytech.core.data.local.model.CityDao
import com.libertytech.core.data.local.model.CityEntity
import javax.inject.Inject

class CityRepository @Inject constructor(private val cityDao: CityDao) {

    suspend fun addCity(name: String, lat: Double, lng: Double) =
        cityDao.insertCity(
            CityEntity(
                name = name,
                lat = lat,
                lng = lng
            )
        )
}