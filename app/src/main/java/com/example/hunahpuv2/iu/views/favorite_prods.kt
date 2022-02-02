package com.example.hunahpuv2.iu.views


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
import com.example.hunahpuv2.iu.viewModel.LocalProductFactory
import com.example.hunahpuv2.iu.viewModel.LocalProductsViewModel
import com.example.hunahpuv2.utils.FavoriteProductsAdapter
import com.google.firebase.auth.FirebaseAuth

class favorite_prods : Fragment() {
    private lateinit var binding: FragmentFavoriteProdsBinding

    private lateinit var viewModel: LocalProductsViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteProdsBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()


        val adapter = FavoriteProductsAdapter(requireActivity().application, firebaseAuth.currentUser!!.uid, this)



        recyclerView = binding.favsRecyclerView
        recyclerView?.apply {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
            recyclerView!!.adapter = adapter
            recyclerView!!.layoutManager = layoutManager

        }


        val productFactory =
            LocalProductFactory(requireActivity().application, firebaseAuth.currentUser!!.uid)
        viewModel = ViewModelProvider(this, productFactory).get(LocalProductsViewModel::class.java)
        viewModel.readAllData.observe(viewLifecycleOwner) { products ->
            adapter.setData(products)
        }

        binding.deleteAllButton.setOnClickListener {
            deleteAllProducts()
        }


        return binding.root
    }

     private fun deleteAllProducts(){
         viewModel.deleteAllProducts()
         Toast.makeText(requireContext(), R.string.allDeleted, Toast.LENGTH_LONG).show()
     }


}