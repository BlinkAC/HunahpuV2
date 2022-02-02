package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class ProductRequestModel(
    @SerializedName("productId") var productId: String? = null,
    @SerializedName("requestedAt") var requestedAt: String? = null
)