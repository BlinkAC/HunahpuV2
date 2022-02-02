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
}