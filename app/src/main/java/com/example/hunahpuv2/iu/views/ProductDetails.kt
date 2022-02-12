package com.example.hunahpuv2.iu.views


import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.hunahpuv2.R
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.data.database.entities.ProductEntity
import com.example.hunahpuv2.data.model.ProductRequest
import com.example.hunahpuv2.data.network.ApiClient
import com.example.hunahpuv2.databinding.ActivityProductDetailsBinding
import com.example.hunahpuv2.iu.viewModel.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
        val productQuantity: TextView = binding.productQuantity
        val bundle: Bundle? = intent.extras
        val pId = bundle?.getString("productId").toString()

        val viewModelFactory = ProductDetailsViewModelFactory(pId, "NL")
        val viewModelDetails = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)

        viewModelDetails.getProductById()
        viewModelDetails.uniqueProductModel.observe(this){ product ->
            if(product != null){
                Picasso.get().load(product.productImage).into(productImage)
                productName.text = product.productName
                productQuantity.text = product.quantity
                viewModelDetails.getPrices()
                viewModelDetails.productPrices.observe(this){
                        prices ->
                    binding.waltmartPrice.text = prices!!.priceWaltmart
                    binding.hebPrice.text = prices.priceHEB
                    binding.sorianaPrice.text = prices.priceSoriana
                    binding.latestUpdate.text = "Ultima actualización: ${prices.latestUpdate!!.subSequence(0,10)}"
                }
            }else{
                 val format = SimpleDateFormat("yyyy-MM-dd")
                val date = format.format(Date())
                showMyDialog(pId, date)

            }

        }

        viewModelDetails.isLoading.observe(this){
                visibility ->
            binding.imageProgress.isVisible = visibility
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

    private fun showMyDialog(productId: String, date: String){
       val builder = MaterialAlertDialogBuilder(this)



        builder.setTitle(R.string.alertNoFound)
        builder.setMessage(R.string.notFoundMsg)
        builder.setNegativeButton(R.string.notNow) { _, _ ->
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setPositiveButton("Si") { _, _ ->
            requestNewProduct(productId, date)
            Toast.makeText(this, R.string.requestConfirm, Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.show()
    }


    private fun requestNewProduct(productId: String, date: String){
        //I know this probably should not be here-
        val retrofit = RetrofitHelper.getRetrofit().create(ApiClient::class.java)
        val requestedProduct = ProductRequest(id =0, productId = productId, requestedAt = date)

        retrofit.requestNewProduct(requestedProduct).enqueue(object : Callback<ProductRequest> {
            override fun onResponse(
                call: Call<ProductRequest>,
                response: Response<ProductRequest>
            ) {
                Log.d("Mensaje", "Se metio bien")
            }

            override fun onFailure(call: Call<ProductRequest>, t: Throwable) {
                Log.d("Mensaje", t.toString())
            }

        })
    }
}