package com.example.hunahpuv2.iu.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LocalProductFactory(private var application: Application, private var userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocalProductsViewModel::class.java)){
            return LocalProductsViewModel(application, userId) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }

}