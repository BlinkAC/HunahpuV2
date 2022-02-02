package com.example.hunahpuv2.data.network

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("APIKEY", "6eb96f00-2ced-4fc6-88b5-337846cbd980")
            .build()
        return  chain.proceed(request)
    }
}