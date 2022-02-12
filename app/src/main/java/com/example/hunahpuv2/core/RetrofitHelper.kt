package com.example.hunahpuv2.core

import com.example.hunahpuv2.data.network.MyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    } .build()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hunahpu.azurewebsites.net/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=25.684493%2C-100.449215&radius=1500&type=supermarket&key=AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40
    fun nearbyPlaces(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //https://maps.googleapis.com/maps/api/place/details/json?fields=name%2Crating%2Cvicinity%2Cformatted_phone_number%2Copening_hours&place_id=ChIJ7w3Bn8KXYoYRgAWHvfvSMEQ&key=AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40
    fun placeDetails(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/details/json?")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //FOTO
    //https://maps.googleapis.com/maps/api/place/photo?maxwidth=700&maxheight=500&photo_reference=Aap_uEDQVn7WxuJt6TtbeTmdleD3ZZCmDwm775pg3y07bJQ09XXI1X6BgYMxfPIFEpFf6vvN_eKz7LXJJO03tm3XwFmv1_Ml31BN_2sRuW4tjsoQ4qF5smqhF4imx7A6871a9gGKo237VnqHe2axYqSR_YFIk41hxtZ0tEXZxI8A9qMQTaCd&key=AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40
}