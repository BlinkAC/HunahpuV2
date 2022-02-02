package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class PriceModel(
    @SerializedName("id") var id: Int? = null,
    //@SerializedName("productId") var productId: String? = null,
    //@SerializedName("stateCode") var stateCode: String? = null,
    @SerializedName("priceWaltmart") var priceWaltmart: String? = null,
    @SerializedName("priceSoriana") var priceSoriana: String? = null,
    @SerializedName("priceHEB") var priceHEB: String? = null,
    @SerializedName("latestUpdate") var latestUpdate: String? = null
)