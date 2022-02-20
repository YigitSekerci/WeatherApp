package com.example.weatherapp.data.providers.location

interface LocationProvider {
    suspend fun getRealLocation(): String
}