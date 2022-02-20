package com.example.weatherapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.data.repository.WeatherInfoRepository
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.states.WeatherInfoHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val repository: WeatherInfoRepository,
) : ViewModel() {

    private val _history = MutableLiveData<WeatherInfoHistoryState>()
    val history: LiveData<WeatherInfoHistoryState> = _history

    fun getHistory() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getWeatherInfoHistory().onEach { resource ->
                when(resource){
                    is Resource.Success -> {
                        _history.value = WeatherInfoHistoryState(weatherInfoHistory = resource.data?.reversed())
                    }
                    is Resource.Error -> {
                        _history.value = WeatherInfoHistoryState(errorMessage = resource.message)
                    }
                    is Resource.Loading ->{
                        _history.value = WeatherInfoHistoryState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
        getHistory()
    }

    fun deleteSpecific(weatherInfoEntity: WeatherInfoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteSpecificWeatherInfo(weatherInfoEntity)
        }
        getHistory()
    }
}