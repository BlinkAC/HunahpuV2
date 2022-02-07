package com.example.hunahpuv2.data.network

import com.example.hunahpuv2.data.model.PriceModel
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.data.model.ProductRequest
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
}

//https://hunahpu.azurewebsites.net/api/Products/GetProductsByName?query=agua

/*https://hunahpu.azurewebsites.net/api/Products?sortByName=asc&pageNumber=1
https://hunahpu.azurewebsites.net/api/Products/bydept?pagenumber=1&departmentid=13

Requested prodicust
https://hunahpu.azurewebsites.net/api/RequestedProducts
{
    "id": 0,
    "productId": "1234567810",
    "requestedAt": "2022-02-02T17:10:57.722Z"
}*/

//https://hunahpu.azurewebsites.net/api/Prices/Get?productCode=75007614&state=CDMX