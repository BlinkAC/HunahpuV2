package com.example.hunahpuv2.data.network

import com.example.hunahpuv2.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//GET POST ETC
interface ApiClient {

    @GET("Products")
    suspend fun getProducts(): Response<List<ProductModel>?>

    @GET("Products/{Id}")
    suspend fun getProductsById(@Path("Id") productId: String): Response<ProductModel>

    @GET("Prices/Get?")
    suspend fun getPrices(@Query("productCode") productCode: String, @Query("state") state: String)
            : Response<PriceModel>

    @GET("Products/GetProductsByName?")
    suspend fun searchProducts(
        @Query("query") query: String?,
        @Query("pageNumber") pageNumber: Int?
    ): Response<List<ProductModel>?>

    @GET("Products/bydept?")
    suspend fun getProductsByDept(
        @Query("pageNumber") pageNumber: Int?,
        @Query("departmentid") departmentid: Int?
    ): Response<List<ProductModel>?>


    @POST("RequestedProducts")
    fun requestNewProduct(@Body requestedProduct: ProductRequest): Call<ProductRequest>

    @GET("nearbysearch/json?")
    suspend fun getNearbyMarkets(
        @Query("location",encoded = true) location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") key: String
    ): Response<LocationsModel>
}

