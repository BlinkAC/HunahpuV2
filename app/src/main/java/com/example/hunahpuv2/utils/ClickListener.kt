package com.example.hunahpuv2.utils

import android.content.Context
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.data.model.ProductModel

interface ClickListener {
    fun OnItemClick(product: ProductModel, context: Context)

}