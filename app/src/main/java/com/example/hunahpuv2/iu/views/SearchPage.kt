package com.example.hunahpuv2.iu.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.example.hunahpuv2.databinding.ActivitySearchPageBinding

class SearchPage : AppCompatActivity() {
    private lateinit var binding : ActivitySearchPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.editTextQuery.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                //Perform Code
                val queryProduct: String = binding.editTextQuery.text.toString()
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("keyword", queryProduct)
                startActivity(intent)
                binding.editTextQuery.text.clear()
                hideKeyboard()

                return@OnKeyListener true
            }
            false
        })

        binding.buttonPanes.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("departmentCode", 9)
            startActivity(intent)
        }

        binding.buttonBebidas.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("departmentCode", 21)
            startActivity(intent)
        }

        binding.buttonBotanas.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("departmentCode", 13)
            startActivity(intent)
        }

        binding.buttonLimpieza.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("departmentCode", "x")
            startActivity(intent)
        }

    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}