package com.libertytech.core.domain.model

data class Weather(
    val description: String,
    val icon: String,
    val temperature: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDirectionInDeg: Int
)