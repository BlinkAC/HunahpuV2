package com.example.hunahpuv2.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.data.model.ProductModel
import com.example.hunahpuv2.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class PagingAdapter: PagingDataAdapter<ProductModel, PagingAdapter.ViewHolder>(DataDiff) {


    class ViewHolder(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductModel){
            Picasso.get().load(product.productImage).into(binding.productImage)
            binding.productName.text = product.productName
        }
    }

    object DataDiff: DiffUtil.ItemCallback<ProductModel>(){

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productName == newItem.productName
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}