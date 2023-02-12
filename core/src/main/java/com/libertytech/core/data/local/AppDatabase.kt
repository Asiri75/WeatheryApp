package com.libertytech.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.libertytech.core.data.local.model.CityDao
import com.libertytech.core.data.local.model.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}