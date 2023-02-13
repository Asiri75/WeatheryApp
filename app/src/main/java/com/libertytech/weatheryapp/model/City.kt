package com.libertytech.weatheryapp.model

data class City(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double
) : java.io.Serializable
