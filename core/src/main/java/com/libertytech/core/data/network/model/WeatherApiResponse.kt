package com.libertytech.core.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse (
    @SerializedName("weather") val weather: List<WeatherResponse>,
    @SerializedName("wind") val wind: WindResponse,
    @SerializedName("main") val temp: TempResponse
)