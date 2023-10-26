package com.snapd.presantation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snapd.databinding.ActivityNetworkBinding

class NetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRefresh.setOnClickListener {
            navigateToTheLoadingScreen()
        }
    }

    private fun navigateToTheLoadingScreen(){
        val intentToTheLoadingScreen = Intent(this, UnoActivity::class.java)
        startActivity(intentToTheLoadingScreen)
    }
}