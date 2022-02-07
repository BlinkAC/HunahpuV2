package com.example.hunahpuv2.iu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.data.paging.ProductPagingSource
import com.example.hunahpuv2.domain.SearchProductsUseCase
import kotlinx.coroutines.launch

class SearchProductsViewModel(
    private val retrofit: RetrofitHelper,
    private val query: String?,
    private val department: Int?
) :
    ViewModel() {

    val productList = Pager(
        PagingConfig(
            initialLoadSize = 10,
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        )
    ) {
        ProductPagingSource(retrofit, query, department)
    }.flow.cachedIn(viewModelScope)

}