package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.model.Location
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("timezone_id")
    val timezone_id: String,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtime_epoch: Int,
    @SerializedName("utc_offset")
    val utc_offset: String
) {
    fun toLocation(): Location {
        return Location(
            country = country,
            name = name,
            region = region
        )
    }
}