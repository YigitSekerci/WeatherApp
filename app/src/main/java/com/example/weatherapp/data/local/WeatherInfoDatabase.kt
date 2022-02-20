package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.data.local.entity.WeatherInfoEntity

@Database(entities = [WeatherInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherInfoDatabase:RoomDatabase() {
    abstract fun weatherInfoDao(): WeatherInfoDao
}