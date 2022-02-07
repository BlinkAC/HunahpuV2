package com.example.hunahpuv2.iu.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hunahpuv2.R
import com.example.hunahpuv2.core.RetrofitHelper
import com.example.hunahpuv2.databinding.ActivitySearchResultBinding
import com.example.hunahpuv2.iu.viewModel.SearchProductsViewModel
import com.example.hunahpuv2.iu.viewModel.SearchViewModelFactory
import com.example.hunahpuv2.utils.EventsLoadStateAdapter
import com.example.hunahpuv2.utils.PagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchProductsViewModel
    private var searchRV: RecyclerView? = null
    private lateinit var binding: ActivitySearchResultBinding
    private var searchProduct: PagingAdapter = PagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle: Bundle? = intent.extras

        val keyword = bundle?.getString("keyword")
        val deparmentCode = bundle?.getInt("departmentCode")

        val productFactory = SearchViewModelFactory(RetrofitHelper, keyword, deparmentCode)

        viewModel = ViewModelProvider(this, productFactory)[SearchProductsViewModel::class.java]

        lifecycleScope.launch{
            viewModel.productList.collectLatest {
                productList ->
                searchProduct.submitData(productList)
            }
        }

        bindUI()
    }

    private fun bindUI() {
        searchRV = binding.searchResult

        searchRV.apply {
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this@SearchResultActivity, 2)
            searchRV!!.layoutManager = layoutManager
            searchRV!!.adapter = searchProduct.withLoadStateHeaderAndFooter(
                header = EventsLoadStateAdapter{ searchProduct.retry() },
                footer = EventsLoadStateAdapter{ searchProduct.retry() }
            )
        }
    }
}