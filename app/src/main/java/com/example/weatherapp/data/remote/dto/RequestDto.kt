package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.model.Request
import com.google.gson.annotations.SerializedName

data class RequestDto(
    @SerializedName("type")
    val type: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("unit")
    val unit: String
){
    fun toRequest(): Request{
        return Request(
            language = language,
            query = query,
            type = type,
            unit = unit
        )
    }
}