package com.libertytech.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    var id: String,
    val name: String,
    val lat: Double,
    val lng: Double
) {
}
