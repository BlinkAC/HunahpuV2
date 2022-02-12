package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class PlaceDetailsModel (
    @SerializedName("result"            ) var result           : Result?           = Result(),
        )

data class Result (

    @SerializedName("formatted_phone_number" ) var formattedPhoneNumber : String?           = null,
    @SerializedName("name"                   ) var name                 : String?           = null,
    @SerializedName("opening_hours"          ) var openingHours         : OpeningHours?     = OpeningHours(),
    @SerializedName("photos"                 ) var photos               : ArrayList<Photos> = arrayListOf(),
    @SerializedName("rating"                 ) var rating               : Double?           = null,
    @SerializedName("vicinity"               ) var vicinity             : String?           = null

)

data class OpeningHours (

    @SerializedName("open_now"     ) var openNow     : Boolean?           = null,
    @SerializedName("weekday_text" ) var weekdayText : ArrayList<String>  = arrayListOf()

)