package com.libertytech.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    val name: String,
    val lat: Double,
    val lng: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
