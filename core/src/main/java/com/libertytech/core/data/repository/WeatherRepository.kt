package com.libertytech.core.data.repository

import com.libertytech.core.data.network.WeatherApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    fun getWeatherFor(lat: Double, lng: Double) = flow {
        emit(
            weatherApi.fetchWeather(
                lat = lat,
                lng = lng
            )
        )
    }
}