package com.example.hunahpuv2.iu.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.database.ProductDatabase
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.data.repository.ProductDbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LocalProductsViewModel(application: Application, userId: String): ViewModel() {

    val readAllData: LiveData<List<ProductEntity>>
    private val repository: ProductDbRepo

    init{
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductDbRepo(productDao, userId)
        readAllData = repository.readAllData
    }

    fun addProduct(product: ProductEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addProduct(product)
        }
    }

    fun deleteProduct(product: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }

    fun deleteAllProducts(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllProducts()
        }
    }
}