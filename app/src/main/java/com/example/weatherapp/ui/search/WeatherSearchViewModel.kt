package com.example.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.providers.location.LocationProvider
import com.example.weatherapp.data.providers.preferences.language.LanguageProvider
import com.example.weatherapp.data.providers.preferences.unit.UnitProvider
import com.example.weatherapp.data.repository.WeatherInfoRepository
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.states.WeatherInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val repository: WeatherInfoRepository,
    private val unitProvider: UnitProvider,
    private val languageProvider: LanguageProvider,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _state = MutableLiveData<WeatherInfoState>()
    val state: LiveData<WeatherInfoState> = _state
    private var _currentUnit: String = "m"
    val currentUnit get() = _currentUnit


    fun searchCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentLocation = locationProvider.getRealLocation()
            getWeather(location = currentLocation)
        }
    }

    fun searchSpecificLocation(location: String) {
        getWeather(location = location)
    }

    fun needCurrentLocation(location: String): Boolean {
        return location == "Current Location"
    }


    private fun getWeather(location: String) {
        val unit = unitProvider.getUnit()
        val language = languageProvider.getLanguage()

        viewModelScope.launch() {
            repository.getWeatherInfo(location = location, lang = language, unit = unit)
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _currentUnit = unit
                            _state.value =
                                WeatherInfoState(weatherInfo = resource.data)
                        }
                        is Resource.Loading -> {
                            _state.value =
                                WeatherInfoState(weatherInfo = state.value?.weatherInfo, isLoading = true)
                        }
                        is Resource.Error -> {
                            _state.value =
                                WeatherInfoState(weatherInfo = state.value?.weatherInfo, errorMessage = resource.message)
                        }
                    }
                }.launchIn(this)
        }
    }

    fun clearErrorMessage(){
        _state.value?.errorMessage = null
    }

}