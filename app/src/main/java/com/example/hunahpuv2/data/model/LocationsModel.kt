package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class LocationsModel (
    @SerializedName("results")
    var results: ArrayList<Results> = arrayListOf()
    )

data class Results(
    @SerializedName("name") var name: String? = null,
    @SerializedName("photos") var photos: ArrayList<Photos> = arrayListOf(),
    @SerializedName("place_id") var placeId: String? = null,
)

data class Photos(
    @SerializedName("photo_reference") var photoReference: String? = null,
)