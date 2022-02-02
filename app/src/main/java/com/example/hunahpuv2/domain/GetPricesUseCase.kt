package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.ProductRepo

class GetPricesUseCase(val productCode: String, val state: String) {
    private val repo = ProductRepo()
    suspend operator fun invoke() = repo.getPrices(productCode, state)
}