package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Location
import com.example.weatherapp.model.Request
import com.example.weatherapp.model.WeatherInfo

@Entity(tableName = "weatherInfoTable")
data class WeatherInfoEntity(
    val feelslike: Double,
    val humidity: Double,
    val wind_speed: Double,
    val wind_dir: String,
    val observation_time: String,
    val temperature: Double,
    val weather_descriptions: List<String>,
    val weather_icons: List<String>,
    val country: String,
    val name: String,
    val region: String,
    val language: String,
    val query: String,
    val type: String,
    val unit: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null
    ){
    fun toWeatherInfo(): WeatherInfo{
        return WeatherInfo(
            Current(
                feelslike = feelslike,
                humidity = humidity,
                observation_time = observation_time,
                temperature = temperature,
                weather_descriptions = weather_descriptions,
                weather_icons = weather_icons,
                wind_dir = wind_dir,
                wind_speed = wind_speed,
            ),
            Location(
                country = country,
                name = name,
                region = region
            ),
            Request(
                language = language,
                query = query,
                type = type,
                unit = unit
            )
        )
    }
}
