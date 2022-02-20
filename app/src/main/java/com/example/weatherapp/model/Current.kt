package com.example.weatherapp.model

data class Current(
                   val feelslike: Double,
                   val wind_speed: Double,
                   val wind_dir: String,
                   val humidity: Double,
                   val observation_time: String,
                   val temperature: Double,
                   val weather_descriptions: List<String>,
                   val weather_icons: List<String>,
                   )
