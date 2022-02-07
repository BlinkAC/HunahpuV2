package com.example.hunahpuv2.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.model.PriceModel
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.data.model.ProductProvider
import com.example.hunahpuv2.data.network.ProductService
import com.example.hunahpuv2.data.paging.ProductPagingSource
import retrofit2.Retrofit

class ProductRepo {

    private val api = ProductService()

    suspend fun getProducts(): List<ProductModel>?{
        val respose = api.getProducts()

        ProductProvider.products = respose
        return respose
    }

    suspend fun getProductById(productId: String): ProductModel?{
        val response = api.getProductById(productId)

        ProductProvider.product = response
        return response
    }

    suspend fun getPrices(productCode: String, state: String): PriceModel{
        val response = api.getPrices(productCode, state)
        ProductProvider.prices = response

        return response

    }

    suspend fun searchProduct(query: String?, page: Int?): List<ProductModel>{
        val respose = api.searchProducts(query, page)

        ProductProvider.products = respose
        return respose
    }

    fun searchResult(retrofit: RetrofitHelper, query: String?, department: Int?) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ProductPagingSource(retrofit, query, department)
        }
    ).liveData
}