package com.example.hunahpuv2.iu.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.R
import com.example.hunahpuv2.data.categoryModel.AllCategories
import com.example.hunahpuv2.databinding.ActivityHomeBinding
import com.example.hunahpuv2.iu.viewModel.productViewModel
import com.example.hunahpuv2.utils.mainAdapter.MainRecyclerViewAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

    private val productViewModel: productViewModel by viewModels()
    private var mainRecyclerView: RecyclerView? = null
    private var allCategory: MutableList<AllCategories> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myToolbar: Toolbar = findViewById(R.id.myToolbar)
        drawerLayout = binding.drawerMenu
        val navView: NavigationView = binding.myNavView

        toggle = ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.open, R.string.close)
        toggle.syncState()
        setSupportActionBar(myToolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        //
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        navView.setNavigationItemSelectedListener {
            //it.isChecked = true
            when(it.itemId){
                R.id.nav_favs -> replaceFragment(favorite_prods())
                R.id.nav_logout -> {
                    firebaseAuth.signOut()
                    checkUser()
                }
            }
            true
        }

        productViewModel.getProducts()
        productViewModel.productModel.observe(this) { productList ->
            allCategory.add(AllCategories("Prueba products", productList))
            setMainRV(allCategory)

        }

        productViewModel.isLoading.observe(this){
            visibility ->
            binding.loading.isVisible = visibility
        }


        binding.scannerButton.setOnClickListener {
            initScanner()
        }
    }

    private fun initScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats("EAN-13", "EAN-8")
        options.setPrompt("Scanee el codigo de barras")
        options.setTorchEnabled(true)
        options.setBeepEnabled(true)
        barcodeLauncher.launch(options)
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, ProductDetails::class.java)
            intent.putExtra("productId", result.contents)
            startActivity(intent)
        }
    }




    private fun setMainRV(allCategory: List<AllCategories>) {
        mainRecyclerView = binding.parentRecyclerView
        mainRecyclerView?.apply {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@HomeActivity)
            mainRecyclerView!!.layoutManager = layoutManager
            adapter = MainRecyclerViewAdapter(allCategory)
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }
}