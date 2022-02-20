package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.model.Current
import com.google.gson.annotations.SerializedName

data class CurrentDto(
    @SerializedName("observation_time")
    val observation_time: String,
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("weather_code")
    val weather_code: Int,
    @SerializedName("weather_icons")
    val weather_icons: List<String>,
    @SerializedName("weather_descriptions")
    val weather_descriptions: List<String>,
    @SerializedName("wind_speed")
    val wind_speed: Double,
    @SerializedName("wind_degree")
    val wind_degree: Double,
    @SerializedName("wind_dir")
    val wind_dir: String,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("cloudcover")
    val cloudcover: Double,
    @SerializedName("feelslike")
    val feelslike: Double,
    @SerializedName("uv_index")
    val uv_index: Double,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("is_day")
    val is_day: String
){
    fun toCurrent(): Current{
        return Current(
            feelslike = feelslike,
            humidity = humidity,
            wind_speed = wind_speed,
            wind_dir = wind_dir,
            observation_time = observation_time,
            temperature = temperature,
            weather_descriptions = weather_descriptions,
            weather_icons = weather_icons,
        )
    }
}