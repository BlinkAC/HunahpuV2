package com.example.hunahpuv2.data.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id"           ) var id           : String?    = null,
    @SerializedName("productName"  ) var productName  : String? = null,
    @SerializedName("quantity"     ) var quantity     : String? = null,
    @SerializedName("productImage" ) var productImage : String? = null
    //var Products: List<ProductData> = emptyList()
)

/*data class ProductData(
    @SerializedName("id"           ) var id           : Int?    = null,
    @SerializedName("productName"  ) var productName  : String? = null,
    @SerializedName("quantity"     ) var quantity     : String? = null,
    @SerializedName("productImage" ) var productImage : String? = null
)*/
