package com.example.hunahpuv2.iu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.database.ProductDatabase
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.data.repository.ProductDbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDbViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ProductEntity>>
    private val repository: ProductDbRepo

    init{
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductDbRepo(productDao)
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