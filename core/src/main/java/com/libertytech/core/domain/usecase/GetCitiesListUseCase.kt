package com.libertytech.core.domain.usecase

import com.libertytech.core.data.repository.CityRepository
import javax.inject.Inject

class GetCitiesListUseCase @Inject constructor(private val cityRepository: CityRepository) {
    fun invoke() = cityRepository.getAllCities()
}