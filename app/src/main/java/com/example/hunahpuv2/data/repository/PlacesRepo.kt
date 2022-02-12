package com.example.hunahpuv2.data.repository

import com.example.hunahpuv2.data.model.LocationsModel
import com.example.hunahpuv2.data.model.PlacesProvider
import com.example.hunahpuv2.data.model.Results
import com.example.hunahpuv2.data.network.PlacesService

class PlacesRepo {
    private val api = PlacesService()

    suspend fun getNearbyPlaces(cords: String, rad: Int, type: String, key: String): List<Results>{
        val response = api.getNearByPlaces(cords, rad, type, key)

        PlacesProvider.places = response.results

        return response.results
    }
}