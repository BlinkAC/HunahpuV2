package com.example.hunahpuv2.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.example.hunahpuv2.data.model.LocationModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context: Context): LiveData<LocationModel>() {

    private var fusedLocation = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocation.lastLocation
            .addOnSuccessListener {
                location: Location? ->
                location?.also { setLocationData(it) }
            }
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocation.removeLocationUpdates(locationCallback)
    }

    private fun setLocationData(location: Location){
        value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    companion object{
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            p0
            for (location in p0.locations){
                setLocationData(location)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        fusedLocation.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

}

/*
* An illegal reflective access operation has occurred
* Illegal reflective access by org.jetbrains.kotlin.kapt3.util.ModuleManipulationUtilsKt (file:/C:/Users/Edgar%20Antonio/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-annotation-processing-gradle/1.6.10/e4f87d7008e67ed12c44b0c2eeca59a038251b9f/kotlin-annotation-processing-gradle-1.6.10.jar) to constructor com.sun.tools.javac.util.Context()
*
* Please consider reporting this to the maintainers of org.jetbrains.kotlin.kapt3.util.ModuleManipulationUtilsKt
*
* Use --illegal-access=warn to enable warnings of further illegal reflective access operations
*
* All illegal access operations will be denied in a future release
* */