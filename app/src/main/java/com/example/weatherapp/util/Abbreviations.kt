package com.example.weatherapp.util

import android.view.inspector.PropertyReader
import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.util.exceptions.NoUnitSelectedException


fun <T> temperatureAbbreviation(data: T): String {
    when (data) {
        is WeatherInfoEntity -> {
            return when (data.unit) {
                "f" -> "°F"
                "m" -> "°C"
                "s" -> "K"
                else -> throw NoUnitSelectedException()
            }
        }
        is String -> {
            return when(data){
                "f" -> "°F"
                "m" -> "°C"
                "s" -> "K"
                else -> throw NoUnitSelectedException()
            }
        }
        else -> {
            throw PropertyReader.PropertyTypeMismatchException(0,"WeatherInfoEntity or String",data!!::class.java.name)
        }
    }
}

fun <T> speedAbbreviation(data: T): String {
    when (data) {
        is WeatherInfoEntity -> {
            return when (data.unit) {
                "f" -> "MPH"
                "m" -> "KMH"
                "s" -> "KMH"
                else -> throw NoUnitSelectedException()
            }
        }
        is String -> {
            return when(data){
                "f" -> "MPH"
                "m" -> "KMH"
                "s" -> "KMH"
                else -> throw NoUnitSelectedException()
            }
        }
        else -> {
            throw PropertyReader.PropertyTypeMismatchException(0,"WeatherInfoEntity or String",data!!::class.java.name)
        }
    }
}