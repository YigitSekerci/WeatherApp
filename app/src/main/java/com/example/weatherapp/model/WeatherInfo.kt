package com.example.weatherapp.model

import com.example.weatherapp.data.local.entity.WeatherInfoEntity

data class WeatherInfo(
    val current: Current,
    val location: Location,
    val request: Request
){
    fun toWeatherInfoEntity(): WeatherInfoEntity{
        return WeatherInfoEntity(
            feelslike = current.feelslike,
            humidity = current.humidity,
            observation_time = current.observation_time,
            temperature = current.temperature,
            weather_descriptions = current.weather_descriptions,
            weather_icons = current.weather_icons,
            country = location.country,
            name = location.name,
            region = location.region,
            language = request.language,
            query = request.query,
            type = request.type,
            unit = request.unit,
            wind_speed = current.wind_speed,
            wind_dir = current.wind_dir
        )
    }
}
