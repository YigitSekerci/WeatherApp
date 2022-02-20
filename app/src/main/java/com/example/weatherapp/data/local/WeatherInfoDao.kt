package com.example.weatherapp.data.local

import androidx.room.*
import com.example.weatherapp.data.local.entity.WeatherInfoEntity

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfoEntity(weatherInfoEntity: WeatherInfoEntity)

    @Query("Select * from weatherInfoTable")
    suspend fun getAllWeatherInfos(): List<WeatherInfoEntity>

    @Query("Delete from weatherInfoTable")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSpecific(weatherInfoEntity: WeatherInfoEntity)
}