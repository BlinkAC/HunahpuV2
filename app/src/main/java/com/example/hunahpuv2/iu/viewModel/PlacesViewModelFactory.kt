@file:Suppress("UNCHECKED_CAST")

package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlacesViewModelFactory( private var cords: String,
                              private var rad: Int,
                              private var type: String,
                              private var key: String): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlacesViewModel::class.java)){
            return PlacesViewModel(cords, rad, type, key) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}