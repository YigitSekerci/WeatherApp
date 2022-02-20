package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.WeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "http://api.weatherstack.com/"

interface WeatherApi {
    @GET("current")
    suspend fun getWeatherWithLanguage(
        @Query("query") location: String,
        @Query("units") unit: String,
        @Query("language") lang: String
    ): WeatherInfoDto

    @GET("current")
    suspend fun getWeatherWithoutLanguage(
        @Query("query") location: String,
        @Query("units") unit: String,
    ): WeatherInfoDto
}