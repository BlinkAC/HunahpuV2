package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.ProductRepo

class GetProductUseCase(val productId: String) {
    private val repo = ProductRepo()
    suspend operator fun invoke() = repo.getProductById(productId)
}