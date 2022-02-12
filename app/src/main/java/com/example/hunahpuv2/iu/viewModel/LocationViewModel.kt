package com.example.hunahpuv2.iu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.repository.DataStoreRepo
import com.example.hunahpuv2.utils.LocationLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData

    private val locationRepo = DataStoreRepo(application)

    val readFromDataStore = locationRepo.readFromDataStore.asLiveData()

    fun saveToDataStore(myLocation: String){
        viewModelScope.launch(Dispatchers.IO) {
            locationRepo.saveToDataStore(myLocation)
        }

    }
}