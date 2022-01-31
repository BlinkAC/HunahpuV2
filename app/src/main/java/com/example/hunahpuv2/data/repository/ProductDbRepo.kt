package com.example.hunahpuv2.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hunahpuv2.data.database.dao.ProductDao
import com.example.hunahpuv2.data.database.entities.ProductEntity

class ProductDbRepo(private val productDao: ProductDao) {

    val readAllData: LiveData<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun addProduct(product: ProductEntity){
        productDao.addProduct(product)
    }

    suspend fun deleteProduct(product: String){
        productDao.deleteProduct(product)
    }

    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }

}