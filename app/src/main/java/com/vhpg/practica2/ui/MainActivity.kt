package com.vhpg.practica2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vhpg.practica2.R
import com.vhpg.practica2.data.ProductRepository
import com.vhpg.practica2.databinding.ActivityMainBinding
import com.vhpg.practica2.ui.fragments.ProductsListFragment
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: ProductRepository
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Practica2)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,ProductsListFragment())
                .commit()
        }
    }
}