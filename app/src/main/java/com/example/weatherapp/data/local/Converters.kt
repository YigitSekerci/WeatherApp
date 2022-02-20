package com.example.weatherapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherapp.data.util.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromJson(json:String): List<String>{
        return jsonParser.fromJson<ArrayList<String>>(json,
            object : TypeToken<ArrayList<String>>(){}.type
            ) ?: emptyList()
    }

    @TypeConverter
    fun toJson(list: List<String>): String{
        return jsonParser.toJson(list,
            object : TypeToken<ArrayList<String>>(){}.type
            )?: "[]"
    }
}