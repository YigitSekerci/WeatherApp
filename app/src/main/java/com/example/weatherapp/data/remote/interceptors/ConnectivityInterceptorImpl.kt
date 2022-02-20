package com.example.weatherapp.data.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.weatherapp.util.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(private val appContext: Context) : ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!hasConnection()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    private fun hasConnection(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)
        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
    }
}