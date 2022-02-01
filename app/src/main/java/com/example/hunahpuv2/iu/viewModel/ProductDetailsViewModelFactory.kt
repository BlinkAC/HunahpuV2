@file:Suppress("UNCHECKED_CAST")

package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductDetailsViewModelFactory(private var productId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)){
            return ProductDetailsViewModel(productId) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}