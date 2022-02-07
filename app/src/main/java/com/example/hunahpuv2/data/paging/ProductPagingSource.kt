package com.example.hunahpuv2.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.data.network.ApiClient
import retrofit2.HttpException
import java.io.IOException


const val STARTING_PAGE_INDEX: Int = 1

class ProductPagingSource(
    private val retrofit: RetrofitHelper,
    private val query: String?,
    private val department: Int?
) :
    PagingSource<Int, ProductModel>() {


    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        if(query == null){
            return try{
                val instance = retrofit.getRetrofit().create(ApiClient::class.java)
                val response = instance.getProductsByDept(position, department).body() ?: emptyList()

                LoadResult.Page(
                    data = response,
                    prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if(response.isEmpty()) null else position + 1
                )
            }catch (exception: IOException){
                return LoadResult.Error(exception)
            }catch (exception: HttpException){
                return LoadResult.Error(exception)
            }
        }else{
            return try{
                val instance = retrofit.getRetrofit().create(ApiClient::class.java)
                val response = instance.searchProducts(query, position).body() ?: emptyList()
                LoadResult.Page(
                    data = response,
                    prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if(response.isEmpty()) null else position + 1
                )

            }catch (exception: IOException){
                return LoadResult.Error(exception)
            }catch (exception: HttpException){
                return LoadResult.Error(exception)
            }
        }


    }
}