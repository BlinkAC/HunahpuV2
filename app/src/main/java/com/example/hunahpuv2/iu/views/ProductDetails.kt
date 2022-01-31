package com.example.hunahpuv2.iu.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.hunahpuv2.R
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.databinding.ActivityProductDetailsBinding
import com.example.hunahpuv2.iu.viewModel.ProductDbViewModel
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {

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
        val pId = bundle?.getString("productId").toString()
        val pImg = bundle?.getString("productImage").toString()
        val pName = bundle?.getString("productName").toString()
        val pQuantity = bundle?.getString("productQuantity").toString()

        Picasso.get().load(pImg).into(productImage)
        productName.text = pName

        binding.addProductToDB.setOnClickListener {
            insertDataToDatabase(pId, pImg, pName, pQuantity)
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
        return !(pId.isNullOrEmpty() && pImg.isNullOrEmpty() && pName.isNullOrEmpty())
    }
}