package com.example.weatherapp.data.remote.interceptors

import okhttp3.Interceptor


const val KEY = "006735dfce200bfc871aabaac249d33b"

val requestInterceptor = Interceptor { chain ->
    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("access_key", KEY)
        .build()
    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@Interceptor chain.proceed(request)
}



