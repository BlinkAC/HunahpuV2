package com.example.hunahpuv2.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hunahpuv2.data.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table WHERE userId = :userId")
    fun getAllProducts(userId: String): LiveData<List<ProductEntity>>

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM product_table WHERE productId = :productId")
    suspend fun deleteProduct(productId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity)
}