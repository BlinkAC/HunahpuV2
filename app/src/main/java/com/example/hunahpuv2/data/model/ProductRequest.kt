package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    @SerializedName("id") val id: Int?,
    @SerializedName("productId") val productId: String?,
    @SerializedName("requestedAt") val requestedAt: String?
)
