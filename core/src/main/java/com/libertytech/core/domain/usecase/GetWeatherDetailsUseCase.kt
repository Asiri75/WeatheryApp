package com.libertytech.core.domain.usecase

import com.libertytech.core.data.network.model.WeatherApiResponse
import com.libertytech.core.data.repository.WeatherRepository
import com.libertytech.core.domain.model.Weather
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherDetailsUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun invoke(lat: Double, lng: Double) = weatherRepository.getWeatherFor(lat, lng).map { mapWeatherResponse(it) }

    /**
     * Map weather API response to simplify its use
     */
    fun mapWeatherResponse(response: WeatherApiResponse) =
        Weather(
            description = response.weather.first().description,
            icon = response.weather.first().icon,
            temperature = response.temp.temp,
            pressure = response.temp.pressure,
            humidity = response.temp.humidity,
            windSpeed = response.wind.speed,
            windDirectionInDeg = response.wind.deg
        )
}