package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.WeatherInfoDao
import com.example.weatherapp.data.local.entity.WeatherInfoEntity
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.exceptions.NoConnectivityException
import com.example.weatherapp.util.exceptions.NotSupportedFeatureException
import com.example.weatherapp.util.exceptions.NullApiObjectException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class WeatherInfoRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherInfoDao
) : WeatherInfoRepository {

    override fun getWeatherInfo(
        location: String,
        lang: String,
        unit: String
    ): Flow<Resource<WeatherInfo>> = flow {

        emit(Resource.Loading())
        try {
            val weatherInfo: WeatherInfo = fetchCurrentWeather(location, lang, unit)
            emit(Resource.Success(weatherInfo))
            persistFetchedCurrentWeather(weatherInfo = weatherInfo)
        } catch (e: NullApiObjectException) {
            emit(Resource.Error(message = "Location couldn't found.Please check your city name."))
        }catch (e: NoConnectivityException){
            emit(Resource.Error(message = "No connectivity. Please open your Internet to search weather."))
        }catch (e: NotSupportedFeatureException){
            emit(Resource.Error(message = "Cannot use other languages because of API subscription plan. Please change your language."))
        } catch (e:Exception){
            emit(Resource.Error(message = e.message))
        }
    }

    private suspend fun fetchCurrentWeather(location: String,
                                            lang: String,
                                            unit: String): WeatherInfo{
        return if(lang != "en"){

            throw NotSupportedFeatureException()

            //Not Supported because of API
            /*val weatherInfoDto = api.getWeatherWithLanguage(location,unit,lang)
            if(weatherInfoDto.currentDto == null ||
                weatherInfoDto.locationDto == null ||
                weatherInfoDto.requestDto == null
            ){
                throw NullApiObjectException()
            }
            weatherInfoDto.toWeatherInfo()*/
        } else{
            val weatherInfoDto = api.getWeatherWithoutLanguage(location,unit)
            if(weatherInfoDto.currentDto == null ||
                weatherInfoDto.locationDto == null ||
                weatherInfoDto.requestDto == null
            ){
                throw NullApiObjectException()
            }
            weatherInfoDto.toWeatherInfo()
        }
    }

    private suspend fun persistFetchedCurrentWeather(weatherInfo: WeatherInfo){
        withContext(Dispatchers.IO) {
            dao.insertWeatherInfoEntity(weatherInfoEntity = weatherInfo.toWeatherInfoEntity())
        }
    }

    override suspend fun deleteAllData() {
        withContext(Dispatchers.IO){
            dao.deleteAll()
        }

    }

    override fun getWeatherInfoHistory():Flow<Resource<List<WeatherInfoEntity>>> = flow{
        emit(Resource.Loading())
        try{
            val weatherInfoHistory:List<WeatherInfoEntity> = fetchWeatherInfoHistory()
            emit(Resource.Success(weatherInfoHistory))
        }catch (e:Exception){
            emit(Resource.Error(e.message))
        }
    }

    private suspend fun fetchWeatherInfoHistory():List<WeatherInfoEntity>{
        return withContext(Dispatchers.IO){
            dao.getAllWeatherInfos()
        }
    }

    override suspend fun deleteSpecificWeatherInfo(weatherInfoEntity: WeatherInfoEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteSpecific(weatherInfoEntity = weatherInfoEntity)
        }
    }


}