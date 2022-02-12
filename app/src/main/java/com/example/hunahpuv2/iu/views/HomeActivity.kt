package com.example.hunahpuv2.iu.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.R
import com.example.hunahpuv2.data.categoryModel.AllCategories
import com.example.hunahpuv2.data.model.Results
import com.example.hunahpuv2.databinding.ActivityHomeBinding
import com.example.hunahpuv2.iu.viewModel.LocationViewModel
import com.example.hunahpuv2.iu.viewModel.PlacesViewModel
import com.example.hunahpuv2.iu.viewModel.PlacesViewModelFactory
import com.example.hunahpuv2.iu.viewModel.productViewModel
import com.example.hunahpuv2.utils.GpsUtils
import com.example.hunahpuv2.utils.PlacesAdapter
import com.example.hunahpuv2.utils.mainAdapter.MainRecyclerViewAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private var isGPSEnabled = false
    private val productViewModel: productViewModel by viewModels()
    private lateinit var locationViewModel: LocationViewModel
    private var mainRecyclerView: RecyclerView? = null
    private var placesRecyclerView: RecyclerView? = null
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

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGpsEnable: Boolean) {
                this@HomeActivity.isGPSEnabled = isGpsEnable
            }
        })

        locationViewModel.readFromDataStore.observe(this) { location ->
            Log.d("Ubicacion", "Ubicacion actual: $location")
            val placesFactory = PlacesViewModelFactory(location, 1500, "supermarket", "AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40")

            val marketsViewModel = ViewModelProvider(this, placesFactory).get(PlacesViewModel::class.java)

            marketsViewModel.getNearbyMarkets()
            marketsViewModel.placesList.observe(this){
                    markets ->
                if (markets != null) {
                    setPlacesRV(markets)
                }
            }
        }



        /*marketsViewModel.getPlacesUseCase
        marketsViewModel.placesList.observe(this){
                markets ->
            Log.d("Market", markets!![0].name.toString()+"su id: "+ markets!![0].placeId.toString())
        }*/



        navView.setNavigationItemSelectedListener {
            //it.isChecked = true
            when (it.itemId) {
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

        productViewModel.isLoading.observe(this) { visibility ->
            binding.loading.isVisible = visibility
        }


        binding.scannerButton.setOnClickListener {
            initScanner()
        }

        binding.myToolbar.searchPageButton.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GPS_REQUEST){
                isGPSEnabled = true
                invokeLocationAction()
            }
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

    private fun setPlacesRV(places: List<Results>){
        placesRecyclerView = binding.placesRV
        placesRecyclerView?.apply {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            placesRecyclerView!!.layoutManager = layoutManager
            adapter = PlacesAdapter(places)
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Log.d("Bien", "Prende el gps")

            isPermissionGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> Log.d("Bien", "Requeire acceso a ubi")

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST

            )
        }
    }

    private fun isPermissionGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    private fun startLocationUpdate() {
        locationViewModel.getLocationData().observe(this) {
            Log.d("Bien", "lon: ${it.longitude} lat: ${it.latitude}")
            locationViewModel.saveToDataStore("${it.latitude}"+"%2C"+"${it.longitude}")

        }
    }


}