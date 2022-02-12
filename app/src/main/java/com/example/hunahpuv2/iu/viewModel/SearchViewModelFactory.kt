package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hunahpuv2.core.RetrofitHelper

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val retrofit: RetrofitHelper,
    private var query: String?,
    private val department: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchProductsViewModel::class.java)) {
            return SearchProductsViewModel(retrofit, query, department) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}