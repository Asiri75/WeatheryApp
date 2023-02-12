package com.libertytech.core.domain.usecase

import com.libertytech.core.data.repository.CityRepository
import javax.inject.Inject

class StoreCityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    suspend fun invoke(name: String, lat: Double, lng: Double) = cityRepository.addCity(name, lat, lng)
}