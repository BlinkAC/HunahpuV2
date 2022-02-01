package com.example.hunahpuv2.data

import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.data.model.ProductProvider
import com.example.hunahpuv2.data.network.ProductService

class ProductRepo {

    private val api = ProductService()

    suspend fun getProducts(): List<ProductModel>?{
        val respose = api.getProducts()

        ProductProvider.products = respose
        return respose
    }

    suspend fun getProductById(productId: Long): ProductModel{
        val response = api.getProductById(productId)

        ProductProvider.product = response
        return response
    }
}