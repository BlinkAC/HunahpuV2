package com.example.hunahpuv2.iu.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.R

import com.example.hunahpuv2.databinding.FragmentFavoriteProdsBinding
import com.example.hunahpuv2.iu.viewModel.ProductDbViewModel
import com.example.hunahpuv2.utils.FavoriteProductsAdapter

class favorite_prods : Fragment() {
    private lateinit var binding: FragmentFavoriteProdsBinding
    private lateinit var mProductDbViewModel: ProductDbViewModel
    private  var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentFavoriteProdsBinding.inflate(inflater,container, false)
       val adapter =  FavoriteProductsAdapter(this)

        recyclerView = binding.favsRecyclerView
        recyclerView?.apply {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
            recyclerView!!.adapter = adapter
            recyclerView!!.layoutManager = layoutManager

        }

        mProductDbViewModel = ViewModelProvider(this).get(ProductDbViewModel::class.java)
        mProductDbViewModel.readAllData.observe(viewLifecycleOwner) { productList ->

                adapter.setData(productList)
               //binding.deleteAllButton.visibility = View.VISIBLE

        }

        binding.deleteAllButton.setOnClickListener {
            deleteAllProducts()
        }


        return binding.root
    }

    private fun deleteAllProducts(){
        mProductDbViewModel.deleteAllProducts()
        Toast.makeText(requireContext(), R.string.allDeleted, Toast.LENGTH_LONG).show()
    }

}