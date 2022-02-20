package com.example.weatherapp.util.states

import com.example.weatherapp.model.WeatherInfo

data class WeatherInfoState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    var errorMessage: String? = null,
)
