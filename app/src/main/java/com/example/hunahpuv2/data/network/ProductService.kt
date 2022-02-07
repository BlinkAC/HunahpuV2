package com.example.hunahpuv2.data.network

import android.util.Log
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.model.PriceModel
import com.example.hunahpuv2.data.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getProducts(): List<ProductModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getProducts()
            if(response.isSuccessful){
                Log.d("Mensaje", "All cool")
            }
            response.body()!!
        }
    }

    suspend fun searchProducts(query: String?, page: Int?): List<ProductModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).searchProducts(query, page)
            if(response.isSuccessful){
                Log.d("Mensaje", "All cool")
            }

            response.body()!!
        }
    }

    suspend fun getProductById(productId: String): ProductModel?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getProductsById(productId)
            if (response.body() != null){
                Log.d("Mensaje", "All cool")
                Log.d("Mensaje", response.body().toString())
                response.body()!!
            }else{
                Log.d("Mensaje", "Malardo")
                Log.d("Mensaje", response.body().toString())
                null
            }

        }
    }

    suspend fun getPrices(productCode: String, state: String): PriceModel{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getPrices(productCode, state)
            if (response.isSuccessful){
                Log.d("Mensaje", "All cool")
            }

            response.body()!!
        }
    }

}