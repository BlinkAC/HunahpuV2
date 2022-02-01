package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.domain.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val productId: Long): ViewModel() {

    val uniqueProductModel = MutableLiveData<ProductModel?>()
    val isLoading = MutableLiveData<Boolean>()
    var getProductUseCase = GetProductUseCase(productId)

    fun getProductById(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getProductUseCase.invoke()
            uniqueProductModel.postValue(result)
            isLoading.postValue(false)
        }
    }

}