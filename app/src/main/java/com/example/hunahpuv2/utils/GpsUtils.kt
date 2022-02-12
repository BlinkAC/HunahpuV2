package com.example.hunahpuv2.utils

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.os.Looper
import android.telephony.CarrierConfigManager
import android.util.Log
import android.widget.Toast
import com.example.hunahpuv2.iu.views.GPS_REQUEST
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient

class GpsUtils(private val context: Context) {

    private var settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
    private var locationSettingsRequest: LocationSettingsRequest? = null
    private var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    init{
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationLiveData.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)

    }

    fun turnGPSOn(OnGPSListener: OnGpsListener?){
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            OnGPSListener?.gpsStatus(true)
        }else{
            settingsClient
                .checkLocationSettings(locationSettingsRequest!!)
                .addOnSuccessListener(context as Activity){
                    //GPS already enable, callback GPS through listener
                    OnGPSListener?.gpsStatus(true)
                }
                .addOnFailureListener(context){ e ->
                    when((e as ApiException).statusCode){
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                            try{
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(context, GPS_REQUEST )
                            }catch (sie: IntentSender.SendIntentException){
                                Log.i("Failure", "Unable to execute request")
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->{
                            val errorMsg = "Ajustes de ubicación inadecuados y no pueden ser arreglados es la aplicacion\nVaya a ajustes"
                            Log.e("Failure", errorMsg)
                            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                        }
                    }

                }
        }
    }

    interface OnGpsListener {
        fun gpsStatus(isGpsEnable: Boolean)
    }


}