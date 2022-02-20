package com.example.weatherapp.data.providers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {
    private val applicationContext = context.applicationContext

    val preferences:SharedPreferences
        get() =PreferenceManager.getDefaultSharedPreferences(applicationContext)
}