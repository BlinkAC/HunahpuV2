package com.example.hunahpuv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId") val productId: String,
    @ColumnInfo (name = "productName") val productName: String,
    @ColumnInfo(name= "productImage") val productImage: String,
    @ColumnInfo(name = "productQuantity") val productQuantity: String,
    @ColumnInfo(name = "userId") val userId: String

)