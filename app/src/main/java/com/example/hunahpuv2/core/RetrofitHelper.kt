package com.example.hunahpuv2.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://azgeirr.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}