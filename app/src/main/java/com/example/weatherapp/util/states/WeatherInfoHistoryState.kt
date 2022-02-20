package com.example.weatherapp.util.states

import com.example.weatherapp.data.local.entity.WeatherInfoEntity

data class WeatherInfoHistoryState(
    val weatherInfoHistory: List<WeatherInfoEntity>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
