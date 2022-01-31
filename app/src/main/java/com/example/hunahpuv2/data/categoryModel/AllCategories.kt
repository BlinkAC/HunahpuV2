package com.example.hunahpuv2.data.categoryModel


import com.example.hunahpuv2.data.model.ProductModel

data class AllCategories(
    var categoryTitle: String, var productList: List<ProductModel>?
)