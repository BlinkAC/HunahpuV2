package com.example.hunahpuv2.data.network

import android.util.Log
import com.example.hunahpuv2.core.RetrofitHelper
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

}