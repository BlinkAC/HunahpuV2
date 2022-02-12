package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hunahpuv2.data.model.Results
import com.example.hunahpuv2.domain.GetNearbyMarketsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel(
    private val cords: String,
    private val rad: Int,
    private val type: String,
    private val key: String
) : ViewModel() {

    val placesList = MutableLiveData<List<Results>?>()

    var getPlacesUseCase = GetNearbyMarketsUseCase(cords, rad, type, key)

    fun getNearbyMarkets(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = getPlacesUseCase.invoke()
            placesList.postValue(result)
        }
    }
}