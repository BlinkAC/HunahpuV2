package com.example.hunahpuv2.iu.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.hunahpuv2.R
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.databinding.ActivityProductDetailsBinding
import com.example.hunahpuv2.iu.viewModel.ProductDbViewModel
import com.example.hunahpuv2.iu.viewModel.ProductDetailsViewModel
import com.example.hunahpuv2.iu.viewModel.ProductDetailsViewModelFactory
import com.squareup.picasso.Picasso

class ProductDetails: AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var mProductViewModel: ProductDbViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mProductViewModel = ViewModelProvider(this).get(ProductDbViewModel::class.java)

        val productImage: ImageView = binding.productImage
        val productName: TextView = binding.productName

        val bundle: Bundle? = intent.extras
        val pId = bundle?.getString("productId")!!.toLong()

        val viewModelFactory = ProductDetailsViewModelFactory(pId)
        val viewModelDetails = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)

        viewModelDetails.getProductById()
        viewModelDetails.uniqueProductModel.observe(this){ product ->
            Picasso.get().load(product!!.productImage).into(productImage)
            productName.text = product.productName
        }

        viewModelDetails.isLoading.observe(this){
                visibility ->
            binding.imageProgress.isVisible = visibility
        }


        binding.addProductToDB.setOnClickListener {
            viewModelDetails.uniqueProductModel.observe(this){
                product ->
                insertDataToDatabase(product!!.id.toString(), product.productImage.toString(),
                product.productName.toString(), product.quantity.toString())
            }
        }
    }

    private fun insertDataToDatabase(pId: String, pImg: String, pName: String, pQuantity: String) {

        if(insertCheck(pId, pImg, pName)){
            val product = ProductEntity(pId, pName, pImg, pQuantity)
            mProductViewModel.addProduct(product)
            Toast.makeText(this, R.string.favSuccess, Toast.LENGTH_LONG).show()
        }

    }

    private fun insertCheck(pId: String, pImg: String, pName: String): Boolean{
        return !(pId.isEmpty() && pImg.isEmpty() && pName.isEmpty())
    }
}