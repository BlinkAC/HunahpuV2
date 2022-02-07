package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.ProductRepo

class SearchProductsUseCase(private val query: String?, private val page: Int?) {
    private val repo = ProductRepo()
    suspend operator fun invoke() = repo.searchProduct(query, page)
}