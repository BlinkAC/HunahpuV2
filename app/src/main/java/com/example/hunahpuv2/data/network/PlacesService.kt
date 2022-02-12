package com.example.hunahpuv2.data.network

import android.util.Log
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.model.LocationsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesService {
    private val retrofit = RetrofitHelper.nearbyPlaces()

    suspend fun getNearByPlaces(cords: String, rad: Int, type: String, key: String): LocationsModel {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getNearbyMarkets(cords, rad, type, key)
            val gato = response.raw().request.url.toString()

            Log.d("Gatito", gato)
            if(response.isSuccessful){
                Log.d("Lugares", response.body().toString())
            }
            response.body()!!
        }
    }
}