package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherInfoRepository {
    fun getWeatherInfo(location : String,lang :String,unit : String) : Flow<Resource<WeatherInfo>>

    suspend fun deleteAllData()

    fun getWeatherInfoHistory() : Flow<Resource<List<WeatherInfoEntity>>>

    suspend fun deleteSpecificWeatherInfo(weatherInfoEntity: WeatherInfoEntity)
}