package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.local.Converters
import com.example.weatherapp.data.local.WeatherInfoDatabase
import com.example.weatherapp.data.providers.location.LocationProvider
import com.example.weatherapp.data.providers.location.LocationProviderImpl
import com.example.weatherapp.data.providers.preferences.language.LanguageProvider
import com.example.weatherapp.data.providers.preferences.language.LanguageProviderImpl
import com.example.weatherapp.data.providers.preferences.unit.UnitProvider
import com.example.weatherapp.data.providers.preferences.unit.UnitProviderImpl
import com.example.weatherapp.data.remote.BASE_URL
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.interceptors.ConnectivityInterceptor
import com.example.weatherapp.data.remote.interceptors.ConnectivityInterceptorImpl
import com.example.weatherapp.data.remote.interceptors.requestInterceptor
import com.example.weatherapp.data.repository.WeatherInfoRepository
import com.example.weatherapp.data.repository.WeatherInfoRepositoryImpl
import com.example.weatherapp.data.util.GsonParser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherInfoModule {


    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext appContext:Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext appContext:Context,fusedLocationProviderClient: FusedLocationProviderClient): LocationProvider {
        return LocationProviderImpl(fusedLocationProviderClient,appContext)
    }

    @Provides
    @Singleton
    fun provideUnitProvider(@ApplicationContext appContext:Context): UnitProvider {
        return UnitProviderImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideLanguageProvider(@ApplicationContext appContext:Context): LanguageProvider {
        return LanguageProviderImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideWeatherInfoRepository(
        db: WeatherInfoDatabase,
        api: WeatherApi
    ): WeatherInfoRepository {
        return WeatherInfoRepositoryImpl(api, db.weatherInfoDao())
    }

    @Provides
    @Singleton
    fun provideWeatherInfoDatabase(app: Application): WeatherInfoDatabase {
        return Room.databaseBuilder(app, WeatherInfoDatabase::class.java, "WeatherInfoDatabase")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(@ApplicationContext appContext: Context): ConnectivityInterceptor{
        return ConnectivityInterceptorImpl(appContext)
    }


    @Provides
    @Singleton
    fun provideWeatherApi(connectivityInterceptor: ConnectivityInterceptor): WeatherApi {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(connectivityInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}