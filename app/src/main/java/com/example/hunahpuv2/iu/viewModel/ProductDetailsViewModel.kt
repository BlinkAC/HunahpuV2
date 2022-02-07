package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.model.PriceModel
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.domain.GetPricesUseCase
import com.example.hunahpuv2.domain.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val productId: String, private val state: String): ViewModel() {

    val uniqueProductModel = MutableLiveData<ProductModel?>()
    val productPrices = MutableLiveData<PriceModel?>()
    val isLoading = MutableLiveData<Boolean>()
    var getProductUseCase = GetProductUseCase(productId)
    var getPrices = GetPricesUseCase(productId, state)

    fun getProductById(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getProductUseCase.invoke()
            uniqueProductModel.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun getPrices(){
        viewModelScope.launch(Dispatchers.IO){
            val result = getPrices.invoke()
            productPrices.postValue(result)
        }
    }

}