package com.example.hunahpuv2.data.network

import com.example.hunahpuv2.data.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

//GET POST ETC
interface ApiClient {

    @GET("Products")
    suspend fun getProducts(): Response<List<ProductModel>?>
}