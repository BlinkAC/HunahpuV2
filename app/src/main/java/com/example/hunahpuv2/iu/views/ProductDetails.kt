package com.example.hunahpuv2.iu.views

import android.app.Application
import android.content.Intent
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
import com.example.hunahpuv2.iu.viewModel.*
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ProductDetails: AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var mProductViewModel: LocalProductsViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val factory = LocalProductFactory(requireNotNull<Application>(this.application), firebaseAuth.currentUser!!.uid)
        mProductViewModel = ViewModelProvider(this, factory).get(LocalProductsViewModel::class.java)

        val productImage: ImageView = binding.productImage
        val productName: TextView = binding.productName

        val bundle: Bundle? = intent.extras
        val pId = bundle?.getString("productId").toString()

        val viewModelFactory = ProductDetailsViewModelFactory(pId, "NL")
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

        viewModelDetails.getPrices()
        viewModelDetails.productPrices.observe(this){
            prices ->
            binding.waltmartPrice.text = prices!!.priceWaltmart
            binding.hebPrice.text = prices.priceHEB
            binding.sorianaPrice.text = prices!!.priceSoriana

        }

        binding.addProductToDB.setOnClickListener {
            viewModelDetails.uniqueProductModel.observe(this){
                product ->
                insertDataToDatabase(product!!.id.toString(), product.productImage.toString(),
                product.productName.toString(), product.quantity.toString(), firebaseAuth.currentUser!!.uid)
            }
        }
    }

    private fun insertDataToDatabase(pId: String, pImg: String, pName: String, pQuantity: String, userId: String) {

        if(insertCheck(pId, pImg, pName)){
            val product = ProductEntity(pId, pName, pImg, pQuantity,userId)
            mProductViewModel.addProduct(product)
            Toast.makeText(this, R.string.favSuccess, Toast.LENGTH_LONG).show()
        }

    }

    private fun insertCheck(pId: String, pImg: String, pName: String): Boolean{
        return !(pId.isEmpty() && pImg.isEmpty() && pName.isEmpty())
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}