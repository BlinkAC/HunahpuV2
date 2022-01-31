package com.example.hunahpuv2.utils.mainAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.data.categoryModel.AllCategories
import com.example.hunahpuv2.data.model.ProductModel

import com.example.hunahpuv2.databinding.ParentItemBinding
import com.example.hunahpuv2.iu.views.ProductDetails
import com.example.hunahpuv2.utils.ClickListener

class MainRecyclerViewAdapter(private val list: List<AllCategories>):
    ClickListener,
    RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder (binding: ParentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var categoryTitle: TextView
        var productRV: RecyclerView

        init {
            categoryTitle = binding.parentItemTitle
            productRV = binding.childRecyclerView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitle.text = list[position].categoryTitle
        setChildRecycleView(holder.productRV.context, holder.productRV,
            list[position].productList!!
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun setChildRecycleView(context: Context?, productRV: RecyclerView, productList: List<ProductModel>) {

        productRV.apply {
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            productRV.layoutManager = layoutManager
            adapter = ProductAdapter(productList, this@MainRecyclerViewAdapter)
        }
    }

    override fun OnItemClick(product: ProductModel, context: Context) {
        val intent = Intent(context, ProductDetails::class.java)
        intent.putExtra("productId", product.id.toString())
        intent.putExtra("productName", product.productName)
        intent.putExtra("productImage", product.productImage)
        intent.putExtra("productQuantity", product.quantity)

        context.startActivity(intent)
    }


}