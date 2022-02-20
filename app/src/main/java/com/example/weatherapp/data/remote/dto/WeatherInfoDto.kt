package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.model.WeatherInfo
import com.google.gson.annotations.SerializedName

data class WeatherInfoDto(
    @SerializedName("request")
    val requestDto: RequestDto,
    @SerializedName("location")
    val locationDto: LocationDto,
    @SerializedName("current")
    val currentDto: CurrentDto
){
    fun toWeatherInfo(): WeatherInfo{
        return WeatherInfo(
            current = currentDto.toCurrent(),
            location = locationDto.toLocation(),
            request = requestDto.toRequest(),
        )
    }
}