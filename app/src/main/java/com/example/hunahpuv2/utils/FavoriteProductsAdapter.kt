package com.example.hunahpuv2.utils

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.R
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.databinding.RowFavoriteItemBinding
import com.example.hunahpuv2.iu.viewModel.LocalProductFactory
import com.example.hunahpuv2.iu.viewModel.LocalProductsViewModel

import com.squareup.picasso.Picasso


class FavoriteProductsAdapter(
    val context: Application,
    val userId: String,
    private val owner: ViewModelStoreOwner
) : RecyclerView.Adapter<FavoriteProductsAdapter.ViewHolder>() {

    private var productList = emptyList<ProductEntity>()
    //var mProductViewModel = ViewModelProvider(context).get(LocalProductsViewModel::class.java)

    private val productFactory =
        LocalProductFactory(context, userId)
    val viewModel = ViewModelProvider(owner, productFactory).get(LocalProductsViewModel::class.java)


    inner class ViewHolder(binding: RowFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var code: TextView = binding.productCode
        private var image: ImageView = binding.favImage
        private var name: TextView = binding.productName
        private var quantity: TextView = binding.productQuantity
        private var button: ImageButton = binding.deleteFavorite


        fun bindInfo(productEntity: ProductEntity) {
            Picasso.get().load(productEntity.productImage).into(image)
            code.text = productEntity.productId
            name.text = productEntity.productName
            quantity.text = productEntity.productQuantity

            button.setOnClickListener {
                deleteProduct(code.text.toString(), image.context)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowFavoriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindInfo(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(products: List<ProductEntity>) {
        this.productList = products
        notifyDataSetChanged()
    }

    fun deleteProduct(id: String, context: Context) {
        viewModel.deleteProduct(id)
        Toast.makeText(context, R.string.singleDelteted, Toast.LENGTH_LONG).show()
    }


}

