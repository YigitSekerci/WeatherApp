package com.example.weatherapp.data.providers.preferences.unit

import android.content.Context
import com.example.weatherapp.data.providers.preferences.PreferenceProvider
import com.example.weatherapp.util.enums.Units
import com.example.weatherapp.util.exceptions.NoUnitSelectedException

const val unit_system = "unit_system"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnit(): String {
        val currentValue: String? = preferences.getString(unit_system, Units.METRIC.name)

        return when (currentValue) {
            Units.METRIC.name -> "m"
            Units.SCIENTIFIC.name -> "s"
            Units.IMPERIAL.name -> "f"
            else -> throw NoUnitSelectedException()
        }
    }

}