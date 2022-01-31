package com.example.hunahpuv2.utils.mainAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.data.model.ProductModel

import com.example.hunahpuv2.databinding.ProductItemBinding
import com.example.hunahpuv2.utils.ClickListener
import com.squareup.picasso.Picasso

class ProductAdapter(val productList: List<ProductModel>, val listener: ClickListener): RecyclerView.Adapter<ProductAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var image: ImageView = binding.productImage
        var name: TextView = binding.productName

        fun bindInfo(product: ProductModel){
            Picasso.get().load(product.productImage).into(image)
            name.text = product.productName

            image.setOnClickListener {
                listener.OnItemClick(product, image.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindInfo(productList[position])
    }

    override fun getItemCount(): Int {
        return 10
    }
}