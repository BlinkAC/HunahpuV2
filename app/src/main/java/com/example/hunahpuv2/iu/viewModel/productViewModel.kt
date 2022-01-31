package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.domain.GetProductsUseCase
import kotlinx.coroutines.launch

class productViewModel: ViewModel() {
    val productModel = MutableLiveData<List<ProductModel>?>()
    //val otherModel = MutableLiveData<ProductModel?>()
    val isLoading = MutableLiveData<Boolean>()

    var getProductsUseCase = GetProductsUseCase()

    fun getProducts(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getProductsUseCase.invoke()
            productModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}