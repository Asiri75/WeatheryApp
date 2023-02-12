package com.libertytech.core.data.network

import com.libertytech.core.data.network.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?units=metric&lang=fr")
    suspend fun fetchWeather(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double
    ): WeatherApiResponse
}