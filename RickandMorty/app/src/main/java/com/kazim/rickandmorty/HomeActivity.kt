package com.kazim.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kazim.rickandmorty.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}