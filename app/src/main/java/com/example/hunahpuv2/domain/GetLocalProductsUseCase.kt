package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.ProductRepo
import com.example.hunahpuv2.data.database.dao.ProductDao
import com.example.hunahpuv2.data.repository.ProductDbRepo

class GetLocalProductsUseCase(productDao: ProductDao, userId: String) {
    private val repo = ProductDbRepo(productDao, userId)
    suspend operator fun invoke() = repo.readAllData
}