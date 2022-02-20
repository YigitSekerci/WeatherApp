package com.example.weatherapp.ui.history

import com.example.weatherapp.data.local.entity.WeatherInfoEntity

interface DeleteListener {
    fun deleteSpecificWeatherHistoryEntry(weatherInfoEntity: WeatherInfoEntity)
}