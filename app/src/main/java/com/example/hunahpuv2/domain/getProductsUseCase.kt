package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.ProductRepo

class GetProductsUseCase {
    private val repo = ProductRepo()
    suspend operator fun invoke() = repo.getProducts()
}