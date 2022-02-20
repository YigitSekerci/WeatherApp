package com.example.weatherapp.data.providers.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.weatherapp.util.exceptions.LocationServiceIsClosedException
import com.example.weatherapp.util.exceptions.NoLocationPermissionException
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CompletableDeferred


class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Context
) : LocationProvider {
    private val appContext = context.applicationContext

    @SuppressLint("MissingPermission")
    override suspend fun getRealLocation(): String {
        if(!hasLocationPermission()){
            throw NoLocationPermissionException()
        }
        if(!hasLocationEnabled()){
            throw LocationServiceIsClosedException()
        }

        val location = CompletableDeferred<String?>()

        fusedLocationProviderClient.lastLocation.apply {
            addOnSuccessListener {
                location.complete("${it.latitude},${it.longitude}")
            }
            addOnFailureListener{
                location.completeExceptionally(it)
            }
        }

        return location.await()!!
    }


    private fun hasLocationEnabled(): Boolean {
        val locationService = Context.LOCATION_SERVICE
        val locationManager = appContext.getSystemService(locationService) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}