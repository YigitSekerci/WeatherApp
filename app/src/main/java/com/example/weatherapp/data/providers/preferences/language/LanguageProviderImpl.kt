package com.example.weatherapp.data.providers.preferences.language

import android.content.Context
import com.example.weatherapp.data.providers.preferences.PreferenceProvider
import com.example.weatherapp.util.enums.Languages
import com.example.weatherapp.util.exceptions.NoLanguageSelectedException

const val chosen_language = "chosen_language"

class LanguageProviderImpl(context: Context): PreferenceProvider(context), LanguageProvider {
    override fun getLanguage(): String {
        val currentLanguage:String? = preferences.getString(chosen_language, Languages.ENGLISH.name)

        return when (currentLanguage){
            Languages.ENGLISH.name -> "en"
            Languages.RUSSIAN.name -> "ru"
            Languages.TURKISH.name -> "tr"
            Languages.ITALIAN.name -> "it"
            else -> throw NoLanguageSelectedException()

        }
    }
}